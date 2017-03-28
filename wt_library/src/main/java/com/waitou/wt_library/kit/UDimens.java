package com.waitou.wt_library.kit;

import android.content.Context;
import android.util.DisplayMetrics;

import com.waitou.wt_library.BaseApplication;

/**
 * Created by waitou on 17/3/24.
 * 大小转换
 */

public class UDimens {

    public static float dip2px(float dp) {
        return dp * BaseApplication.getApp().getResources().getDisplayMetrics().density;
    }

    public static int dip2pxInt(float dp) {
        return (int) (dip2px(dp) + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float scaledDensity = BaseApplication.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    /**
     * 获取屏幕高度
     */
    public static int getDeviceHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getDeviceWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusHeight(Context context) {
        int result = 10;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelOffset(resourceId);
        }
        return result;
    }

}
