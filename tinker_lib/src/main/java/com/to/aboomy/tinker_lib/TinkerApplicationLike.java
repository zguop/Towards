package com.to.aboomy.tinker_lib;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.to.aboomy.tinker_lib.Log.MyLogImp;

/**
 * auth aboom
 * date 2018/4/16
 */

public class TinkerApplicationLike extends DefaultApplicationLike {

    public TinkerApplicationLike(Application application, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, ShareConstants.TINKER_ENABLE_ALL, false, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Application 的 onCreate 在各个进程中都会被调用,所以需要区分进程初始化
        String mainProcessName = getApplication().getApplicationInfo().processName;//主进程
        String processName = ProcessUtil.getProcessName(android.os.Process.myPid());//当前进程
        Log.e("Application", "mainProcessName = " + mainProcessName + "; processName = " + processName);
        String process = "";
        if (!TextUtils.isEmpty(processName) && processName.contains(mainProcessName)) {
            process = processName.replaceAll(mainProcessName, "");
        }
        if (TextUtils.isEmpty(process)) {
            initInMainProcess();
        }
        initInOtherProcess(process);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        TinkerManager.setTinkerApplicationLike(this);
        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);
        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new MyLogImp());

        TinkerManager.installTinker(this);
        Tinker.with(getApplication());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    protected void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    /**
     * 有些sdk只需要在主进程初始化
     */
    protected void initInMainProcess() {

    }

    /**
     * 其他进程的初始化
     */
    protected void initInOtherProcess(String process) {

    }
}
