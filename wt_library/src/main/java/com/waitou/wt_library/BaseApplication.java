package com.waitou.wt_library;

import android.annotation.SuppressLint;
import android.app.Application;

import com.to.aboomy.tinker_lib.TinkerApplicationBase;

/**
 * Created by waitou on 16/12/23.
 */

public class BaseApplication extends TinkerApplicationBase {

    @SuppressLint("StaticFieldLeak")
    private static Application mApp;

    protected BaseApplication() {
        super(BaseApplicationLike.class.getName());
    }

    public static void setApplication(Application application) {
        BaseApplication.mApp = application;
    }

    public static Application getApp() {
        return mApp;
    }
}
