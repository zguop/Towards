package com.to.aboomy.utils_lib;

import android.util.DisplayMetrics;

/**
 * Created by waitou on 17/3/24.
 * 大小转换
 */

public class USize {

    public static float dip2px(float dp) {
        return dp * UtilsContextWrapper.getAppContext().getResources().getDisplayMetrics().density;
    }

    public static int dip2pxInt(float dp) {
        return (int) (dip2px(dp) + 0.5f);
    }

    /**
     * Value of sp to value of px.
     *
     * @param spValue The value of sp.
     * @return value of px
     */
    public static int sp2px(float spValue) {
        final float scaledDensity = UtilsContextWrapper.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    /**
     * 获取屏幕高度
     */
    public static int getDeviceHeight() {
        DisplayMetrics dm = UtilsContextWrapper.getAppContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getDeviceWidth() {
        DisplayMetrics dm = UtilsContextWrapper.getAppContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusHeight() {
        int result = 10;
        int resourceId = UtilsContextWrapper.getAppContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = UtilsContextWrapper.getAppContext().getResources().getDimensionPixelOffset(resourceId);
        }
        return result;
    }

}
