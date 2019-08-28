package com.to.aboomy.theme_lib;

import android.app.Application;
import android.support.annotation.StyleRes;

import com.to.aboomy.theme_lib.compat.SkinActivityLifecycle;
import com.to.aboomy.theme_lib.utils.SkinPreference;


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

    public static void init(Application application) {
        if (skinModeController == null) {
            synchronized (SkinModeController.class) {
                if (skinModeController == null) {
                    skinModeController = new SkinModeController();
                }
            }
        }
        SkinPreference.init(application);
        skinActivityLifecycle = SkinActivityLifecycle.init(application);
    }

    public void changeSkin(@StyleRes int styleResId) {
        skinActivityLifecycle.applySkin(styleResId);
    }
}
