package com.waitou.towards;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.to.aboomy.rx_lib.RxComposite;
import com.to.aboomy.theme_lib.ChangeModeController;
import com.to.aboomy.tinker_lib.TinkerApplicationLike;
import com.to.aboomy.tinker_lib.util.TinkerManager;
import com.waitou.meta_provider_lib.ISubApplication;
import com.waitou.meta_provider_lib.JlMetaProvider;
import com.waitou.net_library.helper.EmptyErrorVerify;
import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.towards.bean.PatchInfo;
import com.waitou.towards.common.ThemeImpl;
import com.waitou.towards.net.DataLoader;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


/**
 * Created by waitou on 17/1/3.
 * application
 */

public class TowardsApplicationLike extends TinkerApplicationLike {

    private List<ISubApplication> subApp;

    public TowardsApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String AppName = ProcessUtils.getCurrentProcessName();
        String mainProcessName = getApplication().getApplicationInfo().processName;//主进程
        Log.e("aa", "appName：" + AppName + " mainProcessName：" + mainProcessName);
        if (!mainProcessName.equals(AppName)) {
            for (ISubApplication iSubApplication : subApp) {
                iSubApplication.onOtherProcess(getApplication(), AppName);
            }
            return;
        }
        //utils工具类的初始化
        initUtils();
        //通过chrome来查看android数据库 chrome://inspect/#devices
        Stetho.initializeWithDefaults(getApplication());
        //初始化主题
        initThemeLib();
        //tinker补丁检查模拟
        tinkerPatch();
        for (ISubApplication iSubApplication : subApp) {
            iSubApplication.onMainCreate(getApplication());
        }
    }

    @Override
    public void onBaseContextAttached(Context base) {
//        MultiDex.install(base);
        super.onBaseContextAttached(base);
        if (subApp == null) {
            JlMetaProvider.register(getApplication(), "SUB_APP", ISubApplication.class);
            subApp = JlMetaProvider.getMetas("SUB_APP");
            for (ISubApplication iSubApplication : subApp) {
                iSubApplication.onBaseContextAttached();
            }
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (ISubApplication iSubApplication : subApp) {
            iSubApplication.onTerminate();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        for (ISubApplication iSubApplication : subApp) {
            iSubApplication.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        for (ISubApplication iSubApplication : subApp) {
            iSubApplication.onTrimMemory(level);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        for (ISubApplication iSubApplication : subApp) {
            iSubApplication.onConfigurationChange(newConfig);
        }
    }

    private void initUtils() {
        Utils.init(getApplication());
        LogUtils.getConfig().setGlobalTag("aa");
    }

    private void initThemeLib() {
        ChangeModeController changeModeController = ChangeModeController.get();
        ThemeImpl themeImpl = new ThemeImpl();
        changeModeController.initConfig(themeImpl);
    }

    private void tinkerPatch() {
        Observable<PatchInfo> observable = DataLoader.getGithubApi().checkPatch()
                .compose(RxTransformerHelper.applySchedulersAndAllFilter(new EmptyErrorVerify()));
        Consumer<PatchInfo> consumer = patchInfo -> {

            if (TextUtils.isEmpty(patchInfo.downloadUrl)) {
                return;
            }

            ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<String>() {
                @Nullable
                @Override
                public String doInBackground() throws Throwable {
//                    Request request = new Request.Builder().url(patchInfo.downloadUrl).build();
//                    Response response = AsyncOkHttpClient.getOkHttpClient().newCall(request).execute();
//                    ResponseBody body = response.body();
//                    if (body != null) {
//                        InputStream inputStream = body.byteStream();
//                        boolean writeFile = FileIOUtils.writeFileFromIS(patchFile.getAbsolutePath(), inputStream);
//                        if (writeFile) {
//                            String md5 = SharePatchFileUtil.getMD5(patchFile);
//                            return patchFile.getAbsolutePath();
//                        }
//                    }
                    return null;
                }

                @Override
                public void onSuccess(@Nullable String result) {
                    if (ObjectUtils.isNotEmpty(result)) {
                        LogUtils.e(" 加载补丁包：" + result);
                        TinkerManager.loadPatch(result);
                    }
                }
            });
        };
        RxComposite.disposableScribe(observable, consumer);
    }
}
