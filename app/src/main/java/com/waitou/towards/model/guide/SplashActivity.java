package com.waitou.towards.model.guide;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;

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
    public boolean initXView() {
        return false;
    }

    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_logo;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setWindowFullScreen(this, true);
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
}