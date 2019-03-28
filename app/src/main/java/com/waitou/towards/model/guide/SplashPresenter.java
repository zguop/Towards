package com.waitou.towards.model.guide;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.waitou.net_library.helper.EmptyErrorVerify;
import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.net_library.http.AsyncOkHttpClient;
import com.waitou.towards.R;
import com.waitou.towards.common.thread.DownloadThread;
import com.waitou.towards.greendao.GreenDaoHelper;
import com.waitou.towards.greendao.LogoImg;
import com.waitou.towards.greendao.LogoImgDao;
import com.waitou.towards.net.DataLoader;
import com.waitou.wt_library.BaseApplication;
import com.waitou.wt_library.base.XPresent;
import com.to.aboomy.utils_lib.USize;
import com.to.aboomy.utils_lib.UFile;
import com.waitou.wt_library.kit.UImage;
import com.to.aboomy.utils_lib.UString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by waitou on 17/3/23.
 * 闪屏 greendao 数据库操作
 */

public class SplashPresenter extends XPresent<SplashActivity> {

    private static final String TAG = SplashPresenter.class.getSimpleName();

    public ObservableField<Drawable> drawable = new ObservableField<>();

    void setLogoImg() {
        //获取数据库所以得logo 图片
        LogoImgDao logoImgDao = GreenDaoHelper.getDaoHelper().getLogoImgDao();
        //查询所有数据
        List<LogoImg> logoImgList = logoImgDao.loadAll();
        //去请求网络上的logo图片
        pend(DataLoader.getGithubApi().getLogoList()
                .compose(RxTransformerHelper.applySchedulersResult(getV(), new EmptyErrorVerify()))
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
                                    if (UString.isNotEmpty(savePath)) {
                                        //删除本地下载的图片
                                        boolean b = UFile.deleteFile(savePath);
                                        Log.e(TAG, "  删除本地图片是否成功 = " + b + " 删除的图片是 ： " + unique.getImgUrl());
                                    }
                                    //删除数据库中的这条数据
                                    logoImgDao.delete(unique);
                                }
                            }
                        } else {
                            Log.e(TAG, " 没有要删除的图片");
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
                    Log.e(TAG, " 插入数据库的图片 ： " + logoImg.getImgUrl());
                    logoImgDao.insert(logoImg); //插入数据 直接开始下载图片
                    downLoaderImg(logoImg.getImgUrl(), logoImg, logoImgDao);
                }));

        if (logoImgList.isEmpty()) {
            initImageResource(ContextCompat.getDrawable(getV(), R.drawable.logo));
            return;
        }
        List<LogoImg> downloadList = new ArrayList<>();
        List<LogoImg> showLogoList = new ArrayList<>();
        // 数据库图片不为0
        for (LogoImg logoImg : logoImgList) { //本地图片一定要存在
            String savePath = logoImg.getSavePath();
            if (UString.isNotEmpty(savePath) && UFile.isFileExists(savePath)) {
                showLogoList.add(logoImg);
            } else {
                downloadList.add(logoImg);
            }
        }

        if (!downloadList.isEmpty()) {
            Log.e(TAG, " 需要下载 ：" + downloadList.size() + " 张图片");
            for (LogoImg logoImg : downloadList) {
                downLoaderImg(logoImg.getImgUrl(), logoImg, logoImgDao);
            }
        }

        Log.e(TAG, " 显示的图片有 " + showLogoList.size() + " 张");
        //如果没有显示的图片 设置默认的图片
        if (showLogoList.isEmpty()) {
            initImageResource(ContextCompat.getDrawable(getV(), R.drawable.logo));
            return;
        }

        List<String> imgUrl; //stream操作 java8的 一大新特性
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            imgUrl = showLogoList.stream()
                    .filter(logoImg -> !logoImg.getIsShowLogoUrl())
                    .map(LogoImg::getSavePath)
                    .collect(Collectors.toList());
        } else {
            imgUrl = new ArrayList<>();
            for (LogoImg logoImg : showLogoList) {
                if (!logoImg.getIsShowLogoUrl()) {
                    imgUrl.add(logoImg.getSavePath());
                }
            }
        }

        Log.e(TAG, " 可以显示的有 " + imgUrl.size() + " 张");
        boolean empty = imgUrl.isEmpty();
        //如果乳品全部都使用过，那么就从showLogoList 重新取一张图片使用
        int random = new Random().nextInt(empty ? showLogoList.size() : imgUrl.size());
        String savePath;
        if (empty) {
            Log.e(TAG, " 获取的是 showLogoList 中的图片 ");
            savePath = showLogoList.get(random).getSavePath();
        } else {
            Log.e(TAG, " 获取的是 imgUrl 中的图片 ");
            savePath = imgUrl.get(random);
        }
        //显示图片
        loadFileImg(savePath);

        if (empty) {
            Log.e(TAG, " 所有图片已经显示， 更新数据库标识");
            //所有图片都已经显示过 去数据库刷新标识
            for (LogoImg logoImg : showLogoList) {
                logoImg.setIsShowLogoUrl(false);
                logoImgDao.update(logoImg);//更新数据
            }
        }
        //去数据库刷新使用过的图片标识
        //查询某个条件的数据
        LogoImg unique = logoImgDao.queryBuilder().where(LogoImgDao.Properties.SavePath.eq(savePath)).unique();
        Log.e(TAG, " 显示的图片是 ： " + unique.getImgUrl());
        unique.setIsShowLogoUrl(true);
        //更新标识
        logoImgDao.update(unique);
    }

    /**
     * 加载图片
     */
    private void loadFileImg(String savePath) {
        File file = new File(savePath);
        Glide.with(getV()).load(file).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<GlideDrawable>(USize.getDeviceWidth(), USize.getDeviceHeight()) {
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                initImageResource(ContextCompat.getDrawable(getV(), R.drawable.logo));
                boolean b = UFile.deleteFile(file);//加载失败的图片需要删除
                Log.e(TAG, "加载失败了 = " + e.toString() + " 删除掉这张图片 = " + b);
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                initImageResource(resource);
            }
        });
    }

    /**
     * 设置img图片并开始动画 延迟100毫秒使动画更流畅
     *
     * @param drawable 需要显示的图片
     */
    private void initImageResource(Drawable drawable) {
        this.drawable.set(drawable);
        pend(Observable.timer(100, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(aLong -> initializeImageConfig()));
    }

    /**
     * 执行动画
     */
    private void initializeImageConfig() {
        Animation animation = AnimationUtils.loadAnimation(getV(), R.anim.splash);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getV().navigateToMain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        getV().animateBackgroundImage(animation);
    }

    /**
     * 主要用下载图片 保存到cache目录
     */
    private void downLoaderImg(String url, LogoImg logoImg, LogoImgDao logoImgDao) {
        String imageCacheSavePath = UImage.getImageCacheSavePath(BaseApplication.getApp(), url);
        DownloadThread.get(0, url, imageCacheSavePath, (id, progress, isCompleted, file) -> {
            if (isCompleted) {
                logoImg.setSavePath(imageCacheSavePath);
                logoImgDao.update(logoImg);
                Log.e(TAG, " save 成功 " + imageCacheSavePath);
            }
        }).setClient(AsyncOkHttpClient.getOkHttpClient()).start();
    }
}
