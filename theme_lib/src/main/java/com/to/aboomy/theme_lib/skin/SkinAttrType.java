package com.to.aboomy.theme_lib.skin;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.to.aboomy.theme_lib.utils.ThemeUtils;

/**
 * auth aboom
 * date 2018/5/27
 */
public enum SkinAttrType {

    BACKGROUD("background") {
        @Override
        public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
            Context context = view.getContext();
            int identifier = ThemeUtils.getIdentifier(context, attrName, attrValueTypeName);
            view.setBackground(ThemeUtils.getDrawable(context, identifier));
        }
    },
    COLOR("textColor") {
        @Override
        public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
            Context context = view.getContext();
            int identifier = ThemeUtils.getIdentifier(context, attrName, attrValueTypeName);
            ((TextView) view).setTextColor(ThemeUtils.getColorStateList(context, identifier));
        }
    },
    SRC("src") {
        @Override
        public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
            Context context = view.getContext();
            int identifier = ThemeUtils.getIdentifier(context, attrName, attrValueTypeName);

            ((ImageView) view).setImageDrawable(ThemeUtils.getDrawable(context, identifier));
        }
    },
    TINT("tint") {
        @Override
        public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Context context = view.getContext();
                int identifier = ThemeUtils.getIdentifier(context, attrName, attrValueTypeName);
                ((ImageView) view).setImageTintList(ThemeUtils.getColorStateList(context, identifier));
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
}
