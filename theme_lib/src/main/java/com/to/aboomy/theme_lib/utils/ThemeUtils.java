package com.to.aboomy.theme_lib.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;

import com.to.aboomy.theme_lib.R;
import com.to.aboomy.theme_lib.appres.AppCompatResources;


/**
 * Created by waitou on 17/1/18.
 */

public class ThemeUtils {

    public static final String ATTR_PREFIX        = "skin"; //开头
    public static final String COLOR_PRIMARY = "colorPrimary";


    private static final String TYPE_DRAWABLE = "drawable"; //资源在drawable目录下
    private static final String TYPE_COLOR = "color"; //资源在color目录下
    private static final String TYPE_ATTR = "attr";

    private static final int[] TEMP_ARRAY = new int[1];

    /**
     * 获取Attr的资源ID
     *
     * @param attrValue AttributeValue
     */
    public static int getAttrResId(String attrValue) {
        if (TextUtils.isEmpty(attrValue)) {
            return 0;
        }

        String resIdStr = attrValue.substring(1);
        return Integer.parseInt(resIdStr);
    }

    /**
     * 获取attrResId指向的颜色ColorStateList AppCompatResources 删除了cache支持代码¬
     *
     * @return ColorStateList
     */
    public static ColorStateList getColorStateList(Context context, int resId) {
        if (context == null) {
            return null;
        }
        return AppCompatResources.getColorStateList(context, resId);
    }

    /**
     * 获取attrResId指向的Drawable  AppCompatResources.getDrawable 走cache的,不支持
     *
     * @return Drawable
     */
    public static Drawable getDrawable(Context context, int resId) {
        if (context == null) {
            return null;
        }
        return ContextCompat.getDrawable(context, resId);
    }

    /**
     * 获取color drawable 目录资源
     *
     * @param resName    资源名字
     * @param defPackage 包名
     * @return 资源id
     */
    public static int getIdentifier(Context context, String resName, String defPackage) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier(resName, TYPE_DRAWABLE, defPackage);
        if (identifier == 0) {
            identifier = resources.getIdentifier(resName, TYPE_COLOR, defPackage);
        }
        if (identifier == 0) {
            identifier = getThemeAttrId(context, resources.getIdentifier(resName, TYPE_ATTR, defPackage));
        }
        return identifier;
    }

    /**
     * 获取attrResId 指向 typedValue
     *
     * @param attrResId attr资源id
     */
    public static TypedValue getAttrTypedValue(Context ctx, @AttrRes int attrResId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = ctx.getTheme();
        theme.resolveAttribute(attrResId, typedValue, true);
        return typedValue;
    }

    public static int getThemeAttrId(Context context, @AttrRes int attr) {
        TEMP_ARRAY[0] = attr;
        TypedArray a = context.obtainStyledAttributes(null, TEMP_ARRAY);
        try {
            return a.getResourceId(0, 0);
        } finally {
            a.recycle();
        }
    }

    public static int getThemeAttrColor(Context context, int attr) {
        TEMP_ARRAY[0] = attr;
        TypedArray a = context.obtainStyledAttributes(null, TEMP_ARRAY);
        try {
            return a.getColor(0, 0);
        } finally {
            a.recycle();
        }
    }

    public static int getColorPrimary(Context context) {
        return getThemeAttrColor(context, R.attr.colorPrimary);
    }
}
