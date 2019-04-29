package com.waitou.towards.view;

import android.content.Context;
import android.util.AttributeSet;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.to.aboomy.theme_lib.config.ThemeUtils;
import com.to.aboomy.theme_lib.skin.SkinCompatSupportable;
import com.waitou.towards.R;

/**
 * auth aboom
 * date 2019-04-29
 */
public class ExNavigationView extends BottomNavigationViewEx implements SkinCompatSupportable {
    public ExNavigationView(Context context) {
        super(context);
    }

    public ExNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void applySkin() {
        setItemIconTintList(ThemeUtils.getColorStateList(getContext(), R.color.skin_bottom_bar_not));
        setItemTextColor(ThemeUtils.getColorStateList(getContext(), R.color.skin_bottom_bar_not));
        setItemBackgroundResource(ThemeUtils.getThemeAttrId(getContext(), R.attr.colorPrimary));
    }
}
