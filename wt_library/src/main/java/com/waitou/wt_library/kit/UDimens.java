package com.waitou.wt_library.kit;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by waitou on 17/3/24.
 */

public class UDimens {

    public static float dip2px(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int dip2pxInt(Context context, float dp) {
        return (int) (dip2px(context, dp) + 0.5f);
    }

    public static int sp2px(Context ctx, float spValue) {
        final float scaledDensity = ctx.getResources().getDisplayMetrics().scaledDensity;
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
}
