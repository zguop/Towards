package com.to.aboomy.statusbar_lib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.os.Build.MANUFACTURER;

public class StatusBarUtil {

    private static final String MANUFACTURER_XIAOMI = "Xiaomi";
    private static final String MANUFACTURER_MEIZU  = "Meizu";

    /**
     * 修改状态栏为全透明，支持5.0以上版本
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void transparencyBar(Activity activity, @ColorInt int color) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        setImmersiveStatusBarBackgroundColor(activity, isNeedDark(color));
    }

    /**
     * 为DrawerLayout 布局设置状态栏颜色,纯色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void drawerLayoutTransparencyBar(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
        drawerLayoutTransparencyBar(activity, color, drawerLayout, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void drawerLayoutTransparencyBar(Activity activity, @ColorInt int color, DrawerLayout drawerLayout, int statusBarAlpha) {
        transparencyBar(activity, color);
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        View statusBar = contentLayout.getChildAt(0);
        if (statusBar != null && statusBar instanceof StatusBarView) {
            if (statusBar.getVisibility() == View.GONE) {
                statusBar.setVisibility(View.VISIBLE);
            }
            statusBar.setBackgroundColor(color);
        } else {
            StatusBarView statusBarView = new StatusBarView(activity);
            statusBarView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
            contentLayout.addView(statusBarView, 0);
        }
        // 内容布局不是 LinearLayout 时,设置padding top
        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1)
                    .setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight(activity) + contentLayout.getPaddingTop(),
                            contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
        }
        setDrawerLayoutProperty(drawerLayout, contentLayout);
    }

    /**
     * 设置 DrawerLayout 属性
     *
     * @param drawerLayout              DrawerLayout
     * @param drawerLayoutContentLayout DrawerLayout 的内容布局
     */
    private static void setDrawerLayoutProperty(DrawerLayout drawerLayout, ViewGroup drawerLayoutContentLayout) {
        ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
        drawerLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setClipToPadding(true);
        drawer.setFitsSystemWindows(false);
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * 设置沉浸式装填栏颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean setImmersiveStatusBarBackgroundColor(Activity activity, @ColorInt int color) {
        boolean isSuc = setImmersiveStatusBarBackgroundColor(activity, isNeedDark(color));
        if (isSuc) {
            setStatusBarColor(activity, color);
        }
        return isSuc;
    }

    /**
     * 根据状态栏颜色解析是否需要暗色状态栏
     *
     * @param color 状态栏颜色
     * @return 是否暗色状态栏
     */
    private static boolean isNeedDark(@ColorInt int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return Math.sqrt(Math.pow((double) (1.0f - hsv[2]), 2.0d) + Math.pow((double) hsv[1], 2.0d)) < 0.5d;
    }

    /**
     * 修改状态栏颜色，支持5.0以上版本
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    /**
     * 设置沉浸式状态栏，因为需要暗色突变，所以需要进行判断是否能设置
     *
     * @param activity 宿主activity
     * @param isDark   是否暗色图标
     * @return 能否设置
     */
    public static boolean setImmersiveStatusBarBackgroundColor(Activity activity, boolean isDark) {
        if (activity == null) {
            return false;
        }
        boolean isSuc = false;
        if (Build.VERSION.SDK_INT >= 23) {
            isSuc = setStatusBarModeFor6_0(activity.getWindow(), isDark);
            if (MANUFACTURER_XIAOMI.equals(MANUFACTURER)) {
                if (setStatusBarFontForMiui(activity.getWindow(), isDark)) {
                    isSuc = true;
                }
            } else if (MANUFACTURER_MEIZU.equals(MANUFACTURER)) {
                if (setStatusBarFontForFlyme(activity.getWindow(), isDark)) {
                    isSuc = true;
                }
            }
        } else if (MANUFACTURER_XIAOMI.equals(MANUFACTURER)) {
            if (setStatusBarFontForMiui(activity.getWindow(), isDark)) {
                isSuc = true;
            }
        } else if (MANUFACTURER_MEIZU.equals(MANUFACTURER)) {
            if (setStatusBarFontForFlyme(activity.getWindow(), isDark)) {
                isSuc = true;
            }
        }
        return isSuc;
    }

    private static boolean setStatusBarFontForMiui(Window window, boolean dark) {
        if (window == null) {
            return false;
        }
        Class clazz = window.getClass();
        try {
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int darkModeFlag = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean setStatusBarFontForFlyme(Window window, boolean dark) {
        if (window == null) {
            return false;
        }
        try {
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean setStatusBarFontForZuk(Window window, boolean dark) {
        if (window == null) {
            return false;
        }
        Class clazz = window.getClass();
        try {
            int color;
            Method setIcon = clazz.getMethod("setStatusBarIconColor", int.class);
            if (dark) {
                color = Color.BLACK;
            } else {
                color = Color.WHITE;
            }
            setIcon.invoke(window, color);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static boolean setStatusBarModeFor6_0(Window window, boolean dark) {
        if (window == null) {
            return false;
        }
        try {
            View decorView = window.getDecorView();
            int ui = decorView.getSystemUiVisibility();
            if (dark) {
                ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(ui);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        } else {
            result = (int) Math.ceil(25 * context.getResources().getDisplayMetrics().density);
        }
        return result;
    }
}

