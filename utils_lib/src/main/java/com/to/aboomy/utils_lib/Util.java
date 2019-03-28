package com.to.aboomy.utils_lib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.WindowManager;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * Created by waitou on 17/3/24.
 * 普通工具
 */

public class Util {

    public static boolean isNotEmptyList(List<?> list) {
        return list != null && list.size() > 0;
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 动态设置窗口全屏
     */
    public static void setWindowFullScreen(Activity activity, boolean fullScreen) {
        if (activity == null || activity.getWindow() == null) {
            return;
        }
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        if (fullScreen) {
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            params.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        } else {
            params.flags |= WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
            params.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        activity.getWindow().setAttributes(params);
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
