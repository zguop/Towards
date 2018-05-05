package com.waitou.three_library;

import android.os.Process;
import android.text.TextUtils;

import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.waitou.wt_library.BaseApplication;
import com.waitou.wt_library.BaseApplicationLike;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * author   itxp
 * date     2018/5/5 20:49
 * des
 */

public class ThreeApplicationLike extends BaseApplicationLike{

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
        String packageName = getPackageName();
        String processName = getProcessName(Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(BaseApplication.getApp());
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(BaseApplication.getApp(), "5df0981fd3", !BuildConfig.DEBUG, strategy);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
