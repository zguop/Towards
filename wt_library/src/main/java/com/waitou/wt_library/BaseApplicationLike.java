package com.waitou.wt_library;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.blankj.utilcode.util.ProcessUtils;
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

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        //Application 的 onCreate 在各个进程中都会被调用,所以需要区分进程初始化
//        String mainProcessName = getApplication().getApplicationInfo().processName;//主进程
//        String processName = ProcessUtil.getProcessName(android.os.Process.myPid());//当前进程
//        Log.e("Application", "mainProcessName = " + mainProcessName + "; processName = " + processName);
//        String process = null;
//        if (!TextUtils.isEmpty(processName) && processName.contains(mainProcessName)) {
//            process = processName.replaceAll(mainProcessName, "");
//        }
//        if (TextUtils.isEmpty(process)) {
//            initInMainProcess();
//        }
//        initInOtherProcess(process);
//    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        BaseApplication.setApplication(getApplication());
    }

    protected void initInMainProcess() {
        /*---------------  内存泄漏的检测 ---------------*/
        if (!BuildConfig.DEBUG || LeakCanary.isInAnalyzerProcess(getApplication())) {
            return;
        }
        LeakCanary.install(getApplication());
    }
}
