package com.waitou.three_library;

import android.app.Application;
import android.content.Intent;
import android.os.Process;

import com.tencent.bugly.crashreport.CrashReport;
import com.to.aboomy.tinker_lib.ProcessUtil;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.waitou.wt_library.BaseApplication;
import com.waitou.wt_library.BaseApplicationLike;

/**
 * author   itxp
 * date     2018/5/5 20:49
 * des
 */

public class ThreeApplicationLike extends BaseApplicationLike{

    public ThreeApplicationLike(Application application, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    protected void initInMainProcess() {
        super.initInMainProcess();
        initUMShare();
        initBugly();
    }

    /**
     * 友盟分享
     */
    private void initUMShare() {
        if (BuildConfig.DEBUG) {
            Config.DEBUG = true;
        }
        //app key 测试文档中的
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        UMShareAPI.get(BaseApplication.getApp());
    }

    /**
     * 初始化Bugly
     */
    private void initBugly() {
        //增加上报进程控制
        String packageName = BaseApplication.getApp().getPackageName();
        String processName = ProcessUtil.getProcessName(Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(BaseApplication.getApp());
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(BaseApplication.getApp(), "5df0981fd3", !BuildConfig.DEBUG, strategy);
    }
}
