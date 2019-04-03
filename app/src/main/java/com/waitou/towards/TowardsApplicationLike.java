package com.waitou.towards;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.to.aboomy.rx_lib.RxComposite;
import com.to.aboomy.rx_lib.RxUtil;
import com.to.aboomy.theme_lib.ChangeModeController;
import com.to.aboomy.tinker_lib.TinkerManager;
import com.to.aboomy.tinker_lib.patch.PatchInfo;
import com.to.aboomy.tinker_lib.patch.ServerUtils;
import com.to.aboomy.tinker_lib.patch.VersionInfo;
import com.waitou.net_library.DataServiceProvider;
import com.waitou.net_library.helper.EmptyErrorVerify;
import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.net_library.http.HttpUtil;
import com.waitou.three_library.ThreeApplicationLike;
import com.waitou.towards.common.ThemeImpl;
import com.waitou.towards.common.thread.DownloadThread;
import com.waitou.towards.net.LoaderService;
import com.waitou.wt_library.BaseApplication;
import com.waitou.wt_library.imageloader.ILFactory;

import java.io.File;

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
        initUtils();
        //初始化网络环境
        HttpUtil.init(BaseApplication.getApp());
        //glide加载初始化
        ILFactory.getLoader().init(BaseApplication.getApp());
        //通过chrome来查看android数据库 chrome://inspect/#devices
        Stetho.initializeWithDefaults(BaseApplication.getApp());
        //初始化主题
        initThemeLib();
        //tinker补丁检查模拟
        tinkerPatch();
    }

    private void initUtils() {
        Utils.init(BaseApplication.getApp());
        LogUtils.getConfig().setGlobalTag("aa");
    }

    private void initThemeLib() {
        ChangeModeController changeModeController = ChangeModeController.get();
        ThemeImpl themeImpl = new ThemeImpl();
        changeModeController.initConfig(themeImpl);
    }

    private void tinkerPatch() {
        Observable<PatchInfo> observable = DataServiceProvider.getInstance().provide(HttpUtil.GITHUB_API, LoaderService
                .class).checkPatch().compose(RxTransformerHelper.applySchedulersResult(new EmptyErrorVerify()));
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
            if (TextUtils.isEmpty(patchInfo.downloadUrl)) {
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
}
