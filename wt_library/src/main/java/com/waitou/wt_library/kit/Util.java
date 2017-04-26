package com.waitou.wt_library.kit;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

import com.waitou.wt_library.base.BaseActivity;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import rx.Subscription;

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
     * 检测context 将 Subscription添加到队列
     */
    public static void contextAddSubscription(Context context, Subscription subscription) {
        if (context instanceof BaseActivity) ((BaseActivity) context).pend(subscription);
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
}
