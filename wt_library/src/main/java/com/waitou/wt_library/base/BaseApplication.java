package com.waitou.wt_library.base;

import android.app.Application;

/**
 * Created by waitou on 16/12/23.
 */

public class BaseApplication extends Application {

    private static BaseApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static BaseApplication getApp() {
        return mApp;
    }


}
