package com.waitou.towards.model;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.waitou.towards.model.main.MainActivity;
import com.waitou.wt_library.base.UIPresent;
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.router.Router;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by waitou on 17/2/3.
 * 启动页
 */

public class SplashActivity extends XActivity {

    @Override
    public UIPresent createPresenter() {
        return null;
    }

    @Override
    public boolean initXView() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setWindowFullScreen(this, true);
        Observable.timer(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Router.newIntent().from(this).to(MainActivity.class).finish().launch();
                });
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void setPresenter(Object presenter) {

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
