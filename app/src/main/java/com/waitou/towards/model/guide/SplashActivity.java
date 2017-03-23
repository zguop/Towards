package com.waitou.towards.model.guide;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.coolerfall.download.DownloadCallback;
import com.coolerfall.download.DownloadManager;
import com.coolerfall.download.DownloadRequest;
import com.coolerfall.download.OkHttpDownloader;
import com.coolerfall.download.Priority;
import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.towards.R;
import com.waitou.towards.databinding.ActivityLogoBinding;
import com.waitou.towards.greendao.GreenDaoHelper;
import com.waitou.towards.greendao.LogoImg;
import com.waitou.towards.greendao.LogoImgDao;
import com.waitou.towards.model.main.MainActivity;
import com.waitou.towards.net.DataLoader;
import com.waitou.towards.net.EmptyErrorVerify;
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.imageloader.ILFactory;
import com.waitou.wt_library.kit.Kits;
import com.waitou.wt_library.router.Router;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by waitou on 17/2/3.
 * 启动页
 */

public class SplashActivity extends XActivity<XPresent, ActivityLogoBinding> {

    private DownloadManager mManager;

    @Override
    public boolean initXView() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_logo;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setWindowFullScreen(this, true);
        long currentTime = System.currentTimeMillis();
        reloadData();
        long cTime = System.currentTimeMillis();
        long temp = cTime - currentTime;
        Log.e("aa", " temp = " + temp);
        if (temp < 2000) {
            pend(Observable.timer(temp < 2000 ? 2000 - temp : temp, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .subscribe(aLong -> Router.newIntent().from(SplashActivity.this).to(MainActivity.class).finish().launch()));
        }
    }


    @Override
    public void reloadData() {
        //获取数据库所以得logo 图片
        LogoImgDao logoImgDao = GreenDaoHelper.getDaoHelper().getLogoImgDao();
        //查询所有数据
        List<LogoImg> logoImgList = logoImgDao.loadAll();
        //去请求网络上的logo图片
        pend(DataLoader.getGithubApi().getLogoList()
                .compose(RxTransformerHelper.applySchedulersResult(this, new EmptyErrorVerify()))
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
                                    if (Kits.UString.isNotEmpty(savePath)) {
                                        //删除本地下载的图片
                                        boolean b = Kits.UFile.deleteFile(savePath);
                                        Log.e("aa", "  删除本地图片是否成功 = " + b + " 删除的图片是 ： " + unique.getImgUrl());
                                    }
                                    //删除数据库中的这条数据
                                    logoImgDao.delete(unique);
                                }
                            }
                        } else {
                            Log.e("aa", " 没有要删除的图片");
                        }
                    }
                })
                .flatMap(Observable::from)
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
                    Log.e("aa", " 插入数据库的图片 ： " + logoImg.getImgUrl());
                    logoImgDao.insert(logoImg); //插入数据
                }));

        if (logoImgList.isEmpty()) {
            getBinding().logoIv.setImageResource(R.drawable.logo);
            return;
        }
        List<LogoImg> downloadList = new ArrayList<>();
        List<LogoImg> showLogoList = new ArrayList<>();
        // 数据库图片不为0
        for (LogoImg logoImg : logoImgList) {
            if (Kits.UString.isNotEmpty(logoImg.getSavePath())) { // 已经下载过的图片
                showLogoList.add(logoImg);
            } else {
                downloadList.add(logoImg);
            }
        }

        if (!downloadList.isEmpty()) {
            Log.e("aa", " 需要下载 ：" + downloadList.size() + " 张图片");
            //去下载图片 存入到数据库
            mManager = new DownloadManager.Builder().context(this)
                    .downloader(OkHttpDownloader.create())
                    .threadPoolSize(2)
                    .build();
            for (LogoImg logoImg : downloadList) {
                String imgUrl = logoImg.getImgUrl();
                String imageSavePath = Kits.UImage.getImageSavePath(this, imgUrl);
                DownloadRequest request = new DownloadRequest.Builder()
                        .uri(Uri.parse(imgUrl))
                        .priority(Priority.HIGH) // 下载最高有限级
                        .allowedNetworkTypes(DownloadRequest.NETWORK_MOBILE)
                        .destinationFilePath(imageSavePath)
                        .downloadCallback(new DownloadCallback() {
                            @Override
                            public void onSuccess(int downloadId, String filePath) {
                                super.onSuccess(downloadId, filePath);
                                Log.e("aa", "下载成功  " + filePath);
                                logoImg.setSavePath(filePath);
                                logoImgDao.update(logoImg);
                            }
                        }).build();

                mManager.add(request);
            }
        }

        Log.e("aa", " 显示的图片有 " + showLogoList.size() + " 张");
        //如果没有显示的图片 设置默认的图片
        if (showLogoList.isEmpty()) {
            getBinding().logoIv.setImageResource(R.drawable.logo);
            return;
        }

        List<String> imgUrl = new ArrayList<>();
        for (LogoImg logoImg : showLogoList) {
            if (!logoImg.getIsShowLogoUrl()) {
                imgUrl.add(logoImg.getSavePath());
            }
        }

        Log.e("aa", " 可以显示的有 " + imgUrl.size() + " 张");
        boolean empty = imgUrl.isEmpty();
        //如果乳品全部都使用过，那么就从showLogoList 重新取一张图片使用
        int random = new Random().nextInt(empty ? showLogoList.size() : imgUrl.size());
        String savePath;
        if (empty) {
            Log.e("aa", " 获取的是 showLogoList 中的图片 ");
            savePath = showLogoList.get(random).getSavePath();
        } else {
            Log.e("aa", " 获取的是 imgUrl 中的图片 ");
            savePath = imgUrl.get(random);
        }
        //显示图片
        ILFactory.getLoader().loadFile(getBinding().logoIv, new File(savePath), null);

        if (empty) {
            Log.e("aa", " 所有图片已经显示， 更新数据库标识");
            //所有图片都已经显示过 去数据库刷新标识
            for (LogoImg logoImg : showLogoList) {
                logoImg.setIsShowLogoUrl(false);
                logoImgDao.update(logoImg);//更新数据
            }
        }

        //去数据库刷新使用过的图片标识
        //查询某个条件的数据
        LogoImg unique = logoImgDao.queryBuilder().where(LogoImgDao.Properties.SavePath.eq(savePath)).unique();
        Log.e("aa", " 显示的图片是 ： " + unique.getImgUrl());
        unique.setIsShowLogoUrl(true);
        //更新标识
        logoImgDao.update(unique);
    }

    /**
     * 动态设置窗口全屏
     */
    private void setWindowFullScreen(Activity activity, boolean fullScreen) {
        if (activity == null || activity.getWindow() == null) {
            return;
        }
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        if (fullScreen) {
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            params.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        } else {
            params.flags |= WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
            params.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        activity.getWindow().setAttributes(params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mManager != null) {
            mManager.cancelAll();
            mManager = null;
        }
    }
}
