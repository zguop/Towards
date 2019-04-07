package com.waitou.towards.model.guide;

import android.os.Bundle;
import android.view.animation.Animation;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.waitou.towards.R;
import com.waitou.towards.databinding.ActivityLogoBinding;
import com.waitou.towards.model.main.MainActivity;
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.router.Router;


/**
 * Created by waitou on 17/2/3.
 * 启动页
 */

public class SplashActivity extends XActivity<SplashPresenter, ActivityLogoBinding> {


    private final long   timeMillis               = 1493887049000L;// 2017-05-04 16:37:29



    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_logo;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        ScreenUtils.setFullScreen(this);
        getBinding().setPresenter(getP());
        reloadData();

        String millis = TimeUtils.getString(timeMillis, 1, TimeConstants.DAY);
        LogUtils.e("millis = " + millis);

        String stringByNow = TimeUtils.getStringByNow(-1, TimeConstants.DAY);

        LogUtils.e("stringByNow = " + stringByNow);

    }

    @Override
    public void reloadData() {
        getP().setLogoImg();
    }

    public void navigateToMain() {
        Router.newIntent().from(this).to(MainActivity.class).finish().launch();
    }

    public void animateBackgroundImage(Animation animation) {
        getBinding().logoIv.startAnimation(animation);
    }
}