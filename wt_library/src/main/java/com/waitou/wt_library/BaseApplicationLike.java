package com.waitou.wt_library;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.billy.android.loading.Gloading;
import com.blankj.utilcode.util.ProcessUtils;
import com.squareup.leakcanary.LeakCanary;
import com.to.aboomy.tinker_lib.TinkerApplicationLike;
import com.waitou.wt_library.adapter.GlobalAdapter;

/**
 * auth aboom
 * date 2018/4/16
 */
public class BaseApplicationLike extends TinkerApplicationLike {

    public BaseApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String AppName = ProcessUtils.getCurrentProcessName();
        String mainProcessName = getApplication().getApplicationInfo().processName;//主进程
        Log.e("aa", "appName：" + AppName + " mainProcessName：" + mainProcessName);
        if (!mainProcessName.equals(AppName)) {
            return;
        }
        initInMainProcess();
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        BaseApplication.setApplication(getApplication());
    }

    protected void initInMainProcess() {
        initLeakCanary();
        Gloading.initDefault(new GlobalAdapter());
    }

    private void initLeakCanary() {
        /*---------------  内存泄漏的检测 ---------------*/
        if (!BuildConfig.DEBUG || LeakCanary.isInAnalyzerProcess(getApplication())) {
            return;
        }
        LeakCanary.install(getApplication());
    }
}
