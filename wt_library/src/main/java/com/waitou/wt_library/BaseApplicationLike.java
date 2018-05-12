package com.waitou.wt_library;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.squareup.leakcanary.LeakCanary;
import com.to.aboomy.tinker_lib.TinkerApplicationLike;

/**
 * auth aboom
 * date 2018/4/16
 */
public class BaseApplicationLike extends TinkerApplicationLike {

    public BaseApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        BaseApplication.setApplication(getApplication());
    }

    @Override
    protected void initInMainProcess() {
        super.initInMainProcess();
        /*---------------  内存泄漏的检测 ---------------*/
        if (!BuildConfig.DEBUG || LeakCanary.isInAnalyzerProcess(getApplication())) {
            return;
        }
        LeakCanary.install(getApplication());
    }

    @Override
    protected void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
    }
}
