package com.waitou.wt_library.tinker;

import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * auth aboom
 * date 2018/4/15
 * 对Tinker的所有api封装
 */
public class TinkerManager {

    private static final String TAG = "tinker";

    private static boolean isInstalled = false;


    public static void installTinker(ApplicationLike applicationLike) {
        if (isInstalled) {
            TinkerLog.w(TAG, "install tinker, but has installed, ignore");
            return;
        }

        TinkerInstaller.install(applicationLike);
        isInstalled = true;
    }


}
