package com.waitou.towards.model;

import android.os.Bundle;

import com.waitou.towards.model.main.MainActivity;
import com.waitou.wt_library.base.UIPresent;
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.router.Router;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by waitou on 17/2/3.
 */

public class SplashActivity extends XActivity {

    @Override
    protected UIPresent createPresenter() {
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
        Observable.timer(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Router.newIntent().from(this).to(MainActivity.class).launch();
                });
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void setPresenter(Object presenter) {

    }
}
