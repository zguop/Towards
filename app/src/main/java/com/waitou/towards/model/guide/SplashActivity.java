package com.waitou.towards.model.guide;

import android.os.Bundle;
import android.view.animation.Animation;

import com.to.aboomy.utils_lib.Util;
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
        Util.setWindowFullScreen(this, true);
        getBinding().setPresenter(getP());
        reloadData();
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