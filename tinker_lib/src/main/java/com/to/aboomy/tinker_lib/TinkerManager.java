/*
 * Tencent is pleased to support the open source community by making Tinker available.
 *
 * Copyright (C) 2016 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.to.aboomy.tinker_lib;

import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.UpgradePatchRetry;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.to.aboomy.tinker_lib.crash.SampleUncaughtExceptionHandler;
import com.to.aboomy.tinker_lib.reporter.SampleLoadReporter;
import com.to.aboomy.tinker_lib.reporter.SamplePatchListener;
import com.to.aboomy.tinker_lib.reporter.SamplePatchReporter;
import com.to.aboomy.tinker_lib.service.TinkerResultService;

public class TinkerManager {
    private static final String TAG = "Tinker.TinkerManager";

    private static ApplicationLike                applicationLike;
    private static SampleUncaughtExceptionHandler uncaughtExceptionHandler;
    private static boolean isInstalled = false;

    public static void setTinkerApplicationLike(ApplicationLike appLike) {
        applicationLike = appLike;
    }

    public static ApplicationLike getTinkerApplicationLike() {
        return applicationLike;
    }

    public static void initFastCrashProtect() {
        if (uncaughtExceptionHandler == null) {
            uncaughtExceptionHandler = new SampleUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
    }

    public static void setUpgradeRetryEnable(boolean enable) {
        UpgradePatchRetry.getInstance(applicationLike.getApplication()).setRetryEnable(enable);
    }

    /**
     * you can specify all class you want.
     * sometimes, you can only install tinker in some process you want!
     */
    public static void installTinker(ApplicationLike appLike) {
        if (isInstalled) {
            TinkerLog.w(TAG, "install tinker, but has installed, ignore");
            return;
        }
        //or you can just use DefaultLoadReporter
        LoadReporter loadReporter = new SampleLoadReporter(appLike.getApplication());
        //or you can just use DefaultPatchReporter
        PatchReporter patchReporter = new SamplePatchReporter(appLike.getApplication());
        //or you can just use DefaultPatchListener
        //校验patch文件是否合法
        PatchListener patchListener = new SamplePatchListener(appLike.getApplication());
        //you can set your own upgrade patch if you need

        AbstractPatch upgradePatchProcessor = new UpgradePatch();

        TinkerInstaller.install(appLike,
                loadReporter, patchReporter, patchListener,
                TinkerResultService.class, upgradePatchProcessor);

        isInstalled = true;
    }

    public static void loadPatch(String path) {
        if (Tinker.isTinkerInstalled()) {
            TinkerInstaller.onReceiveUpgradePatch(applicationLike.getApplication(), path);
        }
    }
}
