package com.waitou.wt_library;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by waitou on 16/12/23.
 */

public class BaseApplication extends Application {

    private static BaseApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;

        /*---------------  内存泄漏的检测 ---------------*/

        if (!BuildConfig.DEBUG || LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
      //  LeakCanary.install(this);
    }

    public static BaseApplication getApp() {
        return mApp;
    }
}
