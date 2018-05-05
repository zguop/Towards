package com.to.aboomy.tinker_lib;

import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * auth aboom
 * date 2018/4/16
 */
public class TinkerManager {

    private static final String TAG = "tinker";

    private static ApplicationLike applicationLike;
    private static boolean isInstalled = false;

    public static void setTinkerApplicationLike(ApplicationLike appLike) {
        applicationLike = appLike;
    }

    public static void installTinker(ApplicationLike applike) {
        if (isInstalled) {
            TinkerLog.w(TAG, "install tinker, but has installed, ignore");
            return;
        }
        TinkerInstaller.install(applike);
        isInstalled = true;
    }




}
