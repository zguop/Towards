package com.waitou.towards;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.facebook.stetho.Stetho;
import com.waitou.net_library.http.HttpUtil;
import com.waitou.three_library.ThreeApplicationLike;
import com.waitou.towards.model.main.MainActivity;
import com.waitou.wt_library.BaseApplication;
import com.waitou.wt_library.imageloader.ILFactory;
import com.waitou.wt_library.kit.AlertToast;
import com.waitou.wt_library.kit.USharedPref;

import java.lang.reflect.Field;


/**
 * Created by waitou on 17/1/3.
 * application
 */

public class TowardsApplicationLike extends ThreeApplicationLike {

    public TowardsApplicationLike(Application application, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    protected void initInMainProcess() {
        super.initInMainProcess();
        //初始化网络环境
        HttpUtil.init(BaseApplication.getApp());
        //初始化sp工具
        USharedPref.init(BaseApplication.getApp());
        //初始化吐司工具
        AlertToast.init(BaseApplication.getApp());
        //glide加载初始化
        ILFactory.getLoader().init(BaseApplication.getApp());
        //通过chrome来查看android数据库 chrome://inspect/#devices
        Stetho.initializeWithDefaults(BaseApplication.getApp());
        //所以activity都会回调的生命周期方法
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

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
                if (activity instanceof MainActivity) {
                    fixInputMethodManagerLeak(activity);
                }
            }
        });
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
