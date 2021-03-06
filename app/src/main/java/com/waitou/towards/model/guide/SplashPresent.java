package com.waitou.towards.model.guide;

import android.graphics.Bitmap;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.towards.R;
import com.waitou.towards.greendao.GreenDaoHelper;
import com.waitou.towards.greendao.LogoImg;
import com.waitou.towards.greendao.LogoImgDao;
import com.waitou.towards.net.DataLoader;
import com.waitou.towards.util.KitUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


/**
 * Created by waitou on 17/3/23.
 * 闪屏 greendao 数据库操作
 */

public class SplashPresent implements SplashContract.SplashPresenter {

    private SplashContract.SplashView view;
    private Disposable subscribe;

    public SplashPresent(SplashContract.SplashView view) {
        this.view = view;
    }

    @Override
    public void setLogoImg() {
        //获取数据库所以得logo 图片
        LogoImgDao logoImgDao = GreenDaoHelper.getDaoHelper().getLogoImgDao();
        //查询所有数据
        List<LogoImg> logoImgList = logoImgDao.loadAll();
        //去请求网络上的logo图片
        subscribe = DataLoader.getGithubApi().getLogoList()
                .compose(RxTransformerHelper.applySchedulersAndAllFilter(null))
                .filter(strings -> strings != null && strings.size() > 0)
                .doOnNext(strings -> {
                    if (!logoImgList.isEmpty()) {
                        if (logoImgList.size() > strings.size()) {
                            //数据库 和 网络获取的url 进行对比不包含的从数据库删除掉
                            for (LogoImg logoImg : logoImgList) {
                                String imgUrl = logoImg.getImgUrl();
                                if (!strings.contains(imgUrl)) {
                                    LogoImg unique = logoImgDao.queryBuilder().where(LogoImgDao.Properties.ImgUrl.eq(imgUrl)).unique();
                                    //查看本地是否下载过图片
                                    String savePath = unique.getSavePath();
                                    if (!StringUtils.isEmpty(savePath)) {
                                        //删除本地下载的图片
                                        boolean b = FileUtils.deleteFile(savePath);
                                        LogUtils.e("  删除本地图片是否成功 = " + b + " 删除的图片是 ： " + unique.getImgUrl());
                                    }
                                    //删除数据库中的这条数据
                                    logoImgDao.delete(unique);
                                }
                            }
                        } else {
                            LogUtils.e(" 没有要删除的图片");
                        }
                    }
                })
                .flatMap(Observable::fromIterable)
                .filter(s -> {
                    if (!logoImgList.isEmpty()) {
                        for (LogoImg logoImg : logoImgList) {
                            if (logoImg.getImgUrl().equals(s)) {
                                return false;
                            }
                        }
                    }
                    return true;
                })
                .subscribe(s -> {
                    LogoImg logoImg = new LogoImg();
                    logoImg.setImgUrl(s);
                    LogUtils.e(" 插入数据库的图片 ： " + logoImg.getImgUrl());
                    logoImgDao.insert(logoImg); //插入数据 直接开始下载图片
                    downLoaderImg(logoImg.getImgUrl(), logoImg, logoImgDao);
                });

        if (logoImgList.isEmpty()) {
            view.initImageResource(view.getImgDrawable(R.drawable.logo));
            return;
        }
        List<LogoImg> downloadList = new ArrayList<>();
        List<LogoImg> showLogoList = new ArrayList<>();
        // 数据库图片不为0
        for (LogoImg logoImg : logoImgList) { //本地图片一定要存在
            String savePath = logoImg.getSavePath();
            if (!StringUtils.isEmpty(savePath) && FileUtils.isFileExists(savePath)) {
                showLogoList.add(logoImg);
            } else {
                downloadList.add(logoImg);
            }
        }

        if (!downloadList.isEmpty()) {
            LogUtils.e(" 需要下载 ：" + downloadList.size() + " 张图片");
            for (LogoImg logoImg : downloadList) {
                downLoaderImg(logoImg.getImgUrl(), logoImg, logoImgDao);
            }
        }

        LogUtils.e(" 显示的图片有 " + showLogoList.size() + " 张");
        //如果没有显示的图片 设置默认的图片
        if (showLogoList.isEmpty()) {
            view.initImageResource(view.getImgDrawable(R.drawable.logo));
            return;
        }

        List<String> imgUrl = Observable.fromIterable(showLogoList)
                .filter(o -> !o.getIsShowLogoUrl())
                .map(LogoImg::getSavePath).toList().blockingGet();

        LogUtils.e(" 可以显示的有 " + imgUrl.size() + " 张");
        boolean empty = imgUrl.isEmpty();
        //如果乳品全部都使用过，那么就从showLogoList 重新取一张图片使用
        int random = new Random().nextInt(empty ? showLogoList.size() : imgUrl.size());
        String savePath;
        if (empty) {
            LogUtils.e(" 获取的是 showLogoList 中的图片 ");
            savePath = showLogoList.get(random).getSavePath();
        } else {
            LogUtils.e(" 获取的是 imgUrl 中的图片 ");
            savePath = imgUrl.get(random);
        }
        //显示图片
        loadFileImg(savePath);

        if (empty) {
            LogUtils.e(" 所有图片已经显示， 更新数据库标识");
            //所有图片都已经显示过 去数据库刷新标识
            for (LogoImg logoImg : showLogoList) {
                logoImg.setIsShowLogoUrl(false);
                logoImgDao.update(logoImg);//更新数据
            }
        }
        //去数据库刷新使用过的图片标识
        //查询某个条件的数据
        LogoImg unique = logoImgDao.queryBuilder().where(LogoImgDao.Properties.SavePath.eq(savePath)).unique();
        LogUtils.e(" 显示的图片是 ： " + unique.getImgUrl());
        unique.setIsShowLogoUrl(true);
        //更新标识
        logoImgDao.update(unique);
    }

    /**
     * 加载图片
     */
    private void loadFileImg(String savePath) {
        File file = new File(savePath);
        LogUtils.e(" 显示的图片本地路径是 ： " + file.getAbsolutePath());
        Bitmap bitmap = ImageUtils.getBitmap(file, ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
        if (bitmap == null) {
            view.initImageResource(view.getImgDrawable(R.drawable.logo));
            boolean b = FileUtils.deleteFile(file);//加载失败的图片需要删除
            LogUtils.e("加载失败了，删除掉这张图片 = " + b);
        } else {
            view.initImageResource(ImageUtils.bitmap2Drawable(bitmap));
        }
    }


    /**
     * 主要用下载图片 保存到cache目录
     */
    private void downLoaderImg(String url, LogoImg logoImg, LogoImgDao logoImgDao) {
        KitUtils.downLoaderImg(url, PathUtils.getInternalAppCachePath(), path -> {
            LogUtils.e("图片下载完成路径 " + path);
            logoImg.setSavePath(path);
            logoImgDao.update(logoImg);
        });
    }

    @Override
    public void onDestroy() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
    }
}
