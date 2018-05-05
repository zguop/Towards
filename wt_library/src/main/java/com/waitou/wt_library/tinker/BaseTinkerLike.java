package com.waitou.wt_library.tinker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * auth aboom
 * date 2018/4/15
 */
@DefaultLifeCycle(application = ".BaseApplication",
flags = ShareConstants.TINKER_ENABLE_ALL,loadVerifyFlag = false)
public class BaseTinkerLike extends ApplicationLike{

    public BaseTinkerLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);

        TinkerManager.installTinker(this);

    }
}


