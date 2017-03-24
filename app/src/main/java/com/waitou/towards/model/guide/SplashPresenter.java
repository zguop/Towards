package com.waitou.towards.model.guide;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.coolerfall.download.DownloadCallback;
import com.coolerfall.download.DownloadManager;
import com.coolerfall.download.DownloadRequest;
import com.coolerfall.download.OkHttpDownloader;
import com.coolerfall.download.Priority;
import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.towards.R;
import com.waitou.towards.greendao.GreenDaoHelper;
import com.waitou.towards.greendao.LogoImg;
import com.waitou.towards.greendao.LogoImgDao;
import com.waitou.towards.net.DataLoader;
import com.waitou.towards.net.EmptyErrorVerify;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.kit.Kits;
import com.waitou.wt_library.kit.UImage;
import com.waitou.wt_library.kit.UString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by waitou on 17/3/23.
 * 闪屏 greendao 数据库操作
 */

public class SplashPresenter extends XPresent<SplashActivity> {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private DownloadManager mManager;

    public ObservableField<Drawable> drawable = new ObservableField<>();

    void setLogoImg() {
        //获取数据库所以得logo 图片
        LogoImgDao logoImgDao = GreenDaoHelper.getDaoHelper().getLogoImgDao();
        //查询所有数据
        List<LogoImg> logoImgList = logoImgDao.loadAll();
        //去请求网络上的logo图片
        getV().pend(DataLoader.getGithubApi().getLogoList()
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
                                        boolean b = Kits.UFile.deleteFile(savePath);
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
                    Log.e(TAG, " 插入数据库的图片 ： " + logoImg.getImgUrl());
                    logoImgDao.insert(logoImg); //插入数据
                }));

        if (logoImgList.isEmpty()) {
            initImageResource(ContextCompat.getDrawable(getV(), R.drawable.logo));
            return;
        }
        List<LogoImg> downloadList = new ArrayList<>();
        List<LogoImg> showLogoList = new ArrayList<>();
        // 数据库图片不为0
        for (LogoImg logoImg : logoImgList) {
            if (UString.isNotEmpty(logoImg.getSavePath())) { // 已经下载过的图片
                showLogoList.add(logoImg);
            } else {
                downloadList.add(logoImg);
            }
        }

        if (!downloadList.isEmpty()) {
            Log.e(TAG, " 需要下载 ：" + downloadList.size() + " 张图片");
            //去下载图片 存入到数据库  //这个downloadManager 后期会替换掉 目前暂时使用
            mManager = new DownloadManager.Builder().context(getV())
                    .downloader(OkHttpDownloader.create())
                    .threadPoolSize(2)
                    .build();
            for (LogoImg logoImg : downloadList) {
                String imgUrl = logoImg.getImgUrl();
                String imageSavePath = UImage.getImageSavePath(getV(), imgUrl);
                DownloadRequest request = new DownloadRequest.Builder()
                        .uri(Uri.parse(imgUrl))
                        .priority(Priority.HIGH) // 下载最高有限级
                        .allowedNetworkTypes(DownloadRequest.NETWORK_MOBILE)
                        .destinationFilePath(imageSavePath)
                        .downloadCallback(new DownloadCallback() {
                            @Override
                            public void onSuccess(int downloadId, String filePath) {
                                super.onSuccess(downloadId, filePath);
                                Log.e(TAG, "下载成功  " + filePath);
                                logoImg.setSavePath(filePath);
                                logoImgDao.update(logoImg);
                            }
                        }).build();
                mManager.add(request);
            }
        }

        Log.e(TAG, " 显示的图片有 " + showLogoList.size() + " 张");
        //如果没有显示的图片 设置默认的图片
        if (showLogoList.isEmpty()) {
            initImageResource(ContextCompat.getDrawable(getV(), R.drawable.logo));
            return;
        }

        List<String> imgUrl = new ArrayList<>();
        for (LogoImg logoImg : showLogoList) {
            if (!logoImg.getIsShowLogoUrl()) {
                imgUrl.add(logoImg.getSavePath());
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

    private void loadFileImg(String savePath) {
        Glide.with(getV()).load(new File(savePath)).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                initImageResource(resource);
            }
        });
    }

    private void initImageResource(Drawable drawable) {
        this.drawable.set(drawable);
        getV().pend(Observable.timer(100, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(aLong -> initializeImageConfig()));
    }

    private void initializeImageConfig() {
        Animation animation = AnimationUtils.loadAnimation(getV(), R.anim.splash);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mManager != null) {
                    mManager.cancelAll();
                    mManager = null;
                }
                getV().navigateToMain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        getV().animateBackgroundImage(animation);
    }
}
