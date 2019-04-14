package com.waitou.three_library;

import android.app.Application;
import android.content.res.Configuration;

import com.blankj.utilcode.util.ProcessUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.waitou.meta_provider_lib.ISubApplication;

/**
 * author   itxp
 * date     2018/5/5 20:49
 * des
 */

public class ThreeApplication implements ISubApplication {

    /**
     * 友盟分享
     */
    private void initUMShare(Application application) {
        if (BuildConfig.DEBUG) {
            Config.DEBUG = true;
        }
        //app key 测试文档中的
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        UMShareAPI.get(application);
    }

    /**
     * 初始化Bugly
     */
    private void initBugly(Application application) {
        //增加上报进程控制
        String packageName = application.getPackageName();
        String processName = ProcessUtils.getCurrentProcessName();
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(application);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(application, "5df0981fd3", !BuildConfig.DEBUG, strategy);
    }

    @Override
    public void onMainCreate(Application application) {
        initUMShare(application);
        initBugly(application);
    }

    @Override
    public void onOtherProcess(Application application, String process) {

    }

    @Override
    public void onBaseContextAttached() {

    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }

    @Override
    public void onConfigurationChange(Configuration newConfig) {

    }
}
