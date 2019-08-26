package com.to.aboomy.theme_lib;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.support.annotation.StyleRes;
import android.support.v4.app.ActivityCompat;

import com.to.aboomy.theme_lib.compat.SkinActivityLifecycle;
import com.to.aboomy.theme_lib.utils.SkinPreference;
import com.to.aboomy.theme_lib.utils.ThemeUtils;


/**
 * 主题切换控制器
 * 支持@开头？开头属性
 * 可自定义主题属性 ?attr/
 * 支出属性 @color/ @drawable/
 * 文件命名 以skin开头
 * 默认定义了colorPrimary colorPrimaryDark colorAccent 主题颜色
 * 支持的属性可在SkinAttrType 枚举中增加
 * 自定义view可实现 SkinCompatSupportable 例子：CustomCircleImageView
 * <p>
 * 使用方式：
 * 1.style定义主题
 * 2.在ThemeEnum增加枚举
 */
public class SkinModeController {

    private static SkinModeController skinModeController;
    private static SkinActivityLifecycle skinActivityLifecycle;

    public static SkinModeController getInstance() {
        return skinModeController;
    }

    public static SkinModeController init(Application application) {
        if (skinModeController == null) {
            synchronized (SkinModeController.class) {
                if (skinModeController == null) {
                    skinModeController = new SkinModeController();
                }
            }
        }
        SkinPreference.init(application);
        skinActivityLifecycle = SkinActivityLifecycle.init(application);
        return skinModeController;
    }

    public void changeSkin(@StyleRes int styleResId) {
        skinActivityLifecycle.applySkin(styleResId);
    }
    /**
     * 刷新 StatusBar
     */
    private void refreshStatusBar(Activity ctx) {
        if (Build.VERSION.SDK_INT >= 21) {
            int resourceId = ThemeUtils.getThemeAttrId(ctx, R.attr.colorPrimaryDark);
            ctx.getWindow().setStatusBarColor(ActivityCompat.getColor(ctx, resourceId));
        }
    }
}
