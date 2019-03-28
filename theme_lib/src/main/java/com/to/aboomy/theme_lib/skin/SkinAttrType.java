package com.to.aboomy.theme_lib.skin;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.to.aboomy.theme_lib.R;
import com.to.aboomy.theme_lib.config.ThemeConstant;
import com.to.aboomy.theme_lib.config.ThemeUtils;

/**
 * auth aboom
 * date 2018/5/27
 */
public enum  SkinAttrType {

    BACKGROUD("background") {
        @Override
        public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
            Context context = view.getContext();
            view.setBackground(ThemeUtils.getDrawable(context, getResId(context, attrName)));
        }
    },
    COLOR("textColor") {
        @Override
        public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
            Context context = view.getContext();
            ((TextView) view).setTextColor(ThemeUtils.getColorStateList(context, getResId(context, attrName)));
        }
    },
    SRC("src") {
        @Override
        public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
            Context context = view.getContext();
            ((ImageView) view).setImageDrawable(ThemeUtils.getDrawable(context, getResId(context, attrName)));
        }
    },
    BACKGROUNDTINT("backgroundTint") {
        @Override
        public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
            Context context = view.getContext();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                view.setBackgroundTintList(ThemeUtils.getColorStateList(context, getResId(context, attrName)));
            }
        }
    }, TINT("tint") {
        @Override
        public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
            Context context = view.getContext();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                ((ImageView) view).setImageTintList(ThemeUtils.getColorStateList(context, getResId(context, attrName)));
            }
        }
    };

    private String attrType;

    SkinAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrType() {
        return attrType;
    }

    public abstract void apply(View view, String attrName, String attrValueResName, String attrValueTypeName);

    public int getResId(Context context, String resName) {
        switch (resName) {
            case ThemeConstant.COLOR_PRIMARY:
                return ThemeUtils.getThemeAttrId(context, R.attr.colorPrimary);
            case ThemeConstant.COLOR_PRIMARY_DARK:
                return ThemeUtils.getThemeAttrId(context, R.attr.colorPrimaryDark);
            case ThemeConstant.COLOR_ACCENT:
                return ThemeUtils.getThemeAttrId(context, R.attr.colorAccent);
            default:
                return ThemeUtils.getIdentifier(context, resName, context.getPackageName());
        }
    }
}
