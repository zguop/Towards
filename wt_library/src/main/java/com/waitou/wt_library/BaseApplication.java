package com.waitou.wt_library;

import android.app.Application;
import android.content.res.Configuration;

import com.billy.android.loading.Gloading;
import com.squareup.leakcanary.LeakCanary;
import com.waitou.meta_provider_lib.ISubApplication;
import com.waitou.wt_library.adapter.GlobalAdapter;

/**
 * auth aboom
 * date 2019/4/14
 */
public class BaseApplication implements ISubApplication {

    @Override
    public void onMainCreate(Application application) {
        Gloading.initDefault(new GlobalAdapter());
        initLeakCanary(application);
    }

    private void initLeakCanary(Application application) {
        /*---------------  内存泄漏的检测 ---------------*/
        if (!BuildConfig.DEBUG || LeakCanary.isInAnalyzerProcess(application)) {
            return;
        }
        LeakCanary.install(application);
    }

    @Override
    public void onOtherProcess(Application application, String process) {

    }

    @Override
    public void onBaseContextAttached() {

    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }

    @Override
    public void onConfigurationChange(Configuration newConfig) {

    }
}
