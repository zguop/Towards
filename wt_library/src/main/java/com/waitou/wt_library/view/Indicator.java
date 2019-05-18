package com.waitou.wt_library.view;

import android.content.Context;

import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.theme_lib.config.ThemeUtils;
import com.to.aboomy.theme_lib.skin.SkinCompatSupportable;
import com.waitou.wt_library.R;

/**
 * auth aboom
 * date 2018/7/8
 */
public class Indicator extends IndicatorView implements SkinCompatSupportable {
    public Indicator(Context context) {
        super(context);
    }

    @Override
    public void applySkin() {
        setIndicatorInColor(ThemeUtils.getThemeAttrColor(getContext(), R.attr.colorPrimary));
    }
}
