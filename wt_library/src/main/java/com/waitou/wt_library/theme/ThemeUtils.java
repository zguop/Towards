package com.waitou.wt_library.theme;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.util.TypedValue;

import com.waitou.wt_library.R;
import com.waitou.wt_library.theme.appres.AppCompatResources;

import java.lang.reflect.Field;



/**
 * Created by waitou on 17/1/18.
 */

public class ThemeUtils {

    private static final String TYPE_DRAWABLE = "drawable"; //资源在drawable目录下
    private static final String TYPE_COLOR    = "color"; //资源在color目录下
    private static final String TYPE_ATTR     = "attr";

    private static final int[] TEMP_ARRAY = new int[1];

    /**
     * 获取Attr的资源ID
     *
     * @param attrValue AttributeValue
     */
    public static int getAttrResId(String attrValue) {
        if (TextUtils.isEmpty(attrValue)) {
            return -1;
        }

        String resIdStr = attrValue.substring(1);
        return Integer.valueOf(resIdStr);
    }

    /**
     * 获取attrResId指向的颜色ColorStateList
     *
     * @return ColorStateList
     */
    public static ColorStateList getColorStateList(Context context,@ColorRes int color) {
        if (context == null) {
            return null;
        }

        return AppCompatResources.getColorStateList(context, color);
    }

    /**
     * 获取attrResId指向的Drawable
     *
     * @return Drawable
     */
    public static Drawable getDrawable(Context context, @ColorRes int color) {
        if (context == null) {
            return null;
        }
        return AppCompatResources.getDrawable(context, color);
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
        if (identifier == 0) {
            throw new Resources.NotFoundException("未找到资源");
        }
        return identifier;
    }


    /**
     * 反射获取文件id
     *
     * @param attrName 属性名称
     * @return 属性id
     */
    public static int getAttr(Class draw, String attrName) {
        try {
            Field field = draw.getDeclaredField(attrName);
            //field.setAccessible(true);
            return field.getInt(attrName);
        } catch (Exception e) {
            return R.attr.colorPrimary;
        }
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

}
