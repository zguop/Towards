package com.waitou.towards;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.facebook.stetho.Stetho;
import com.to.aboomy.rx_lib.RxComposite;
import com.to.aboomy.rx_lib.RxUtil;
import com.to.aboomy.theme_lib.ChangeModeController;
import com.to.aboomy.tinker_lib.TinkerManager;
import com.to.aboomy.tinker_lib.patch.PatchInfo;
import com.to.aboomy.tinker_lib.patch.ServerUtils;
import com.to.aboomy.tinker_lib.patch.VersionInfo;
import com.to.aboomy.utils_lib.UActivity;
import com.to.aboomy.utils_lib.UString;
import com.to.aboomy.utils_lib.UtilsContextWrapper;
import com.waitou.net_library.DataServiceProvider;
import com.waitou.net_library.helper.EmptyErrorVerify;
import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.net_library.http.HttpUtil;
import com.waitou.three_library.ThreeApplicationLike;
import com.waitou.towards.common.ThemeImpl;
import com.waitou.towards.common.thread.DownloadThread;
import com.waitou.towards.model.main.MainActivity;
import com.waitou.towards.net.LoaderService;
import com.waitou.wt_library.BaseApplication;
import com.waitou.wt_library.imageloader.ILFactory;

import java.io.File;
import java.lang.reflect.Field;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


/**
 * Created by waitou on 17/1/3.
 * application
 */

public class TowardsApplicationLike extends ThreeApplicationLike {

    public TowardsApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    protected void initInMainProcess() {
        super.initInMainProcess();
        //utils工具类的初始化
        UtilsContextWrapper.init(BaseApplication.getApp());
        //初始化网络环境
        HttpUtil.init(BaseApplication.getApp());
        //glide加载初始化
        ILFactory.getLoader().init(BaseApplication.getApp());
        //通过chrome来查看android数据库 chrome://inspect/#devices
        Stetho.initializeWithDefaults(BaseApplication.getApp());
        //所以activity都会回调的生命周期方法
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                UActivity.getActivityList().add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (UActivity.getActivityList().contains(activity)) {
                    UActivity.getActivityList().remove(activity);
                }
                if (activity instanceof MainActivity) {
                    fixInputMethodManagerLeak(activity);
                }
            }
        });
        initThemeLib();
        tinkerPatch();
    }

    private void initThemeLib() {
        ChangeModeController changeModeController = ChangeModeController.get();
        ThemeImpl themeImpl = new ThemeImpl();
        changeModeController.initConfig(themeImpl);
    }

    private void tinkerPatch() {
        Observable<PatchInfo> observable = DataServiceProvider.getInstance().provide(HttpUtil.GITHUB_API, LoaderService
                .class).checkPatch().compose(RxTransformerHelper.applySchedulersResult(getApplication(), new EmptyErrorVerify()));

        Consumer<PatchInfo> consumer = patchInfo -> {
            File patchFile = ServerUtils.getServerFile(getApplication(), patchInfo.versionName);
            if (patchFile.exists()) {
                if (patchFile.delete()) {
                    Log.i("aa", patchFile.getName() + " delete");
                }
            }
            VersionInfo v = VersionInfo.getInstance();
            if (!v.isUpdate(patchInfo.patchVersion, patchInfo.versionName)) {
                return;
            }
            if (UString.isEmpty(patchInfo.downloadUrl)) {
                return;
            }
            DownloadThread.get(0, patchInfo.downloadUrl, patchFile.getAbsolutePath()
                    , (id, progress, isCompleted, file) ->
                            RxUtil.safelyTask(() -> {
                                Log.e("aa", "loader patch");
                                TinkerManager.loadPatch(file.getPath());
                            })
            );
        };

        RxComposite.disposableScribe(observable, consumer);

    }

    private void fixInputMethodManagerLeak(Context destContext) {
        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f;
        Object obj_get;
        for (String param : arr) {
            try {
                f = imm.getClass().getDeclaredField(param);
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    }
                    break;
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }
}
