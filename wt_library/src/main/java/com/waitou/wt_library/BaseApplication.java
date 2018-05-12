package com.waitou.wt_library;

import android.annotation.SuppressLint;
import android.app.Application;

/**
 * Created by waitou on 16/12/23.
 */
public class BaseApplication{

    @SuppressLint("StaticFieldLeak")
    private static Application mApp;

    public static void setApplication(Application application) {
        BaseApplication.mApp = application;
    }

    public static Application getApp() {
        return mApp;
    }
}
