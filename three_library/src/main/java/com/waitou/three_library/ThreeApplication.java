package com.waitou.three_library;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.waitou.wt_library.BaseApplication;

/**
 * Created by waitou on 17/5/16.
 */

public class ThreeApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initUMShare();
    }

    /**
     * 友盟分享
     */
    private void initUMShare() {
        if (BuildConfig.DEBUG) {
            Config.DEBUG = true;
        }
        //app key 测试文档中的
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        UMShareAPI.get(this);
    }
}
