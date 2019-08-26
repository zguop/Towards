package com.waitou.towards.view;

import android.content.Context;
import android.util.AttributeSet;

import com.to.aboomy.theme_lib.utils.ThemeUtils;
import com.to.aboomy.theme_lib.compat.SkinCompatSupportable;
import com.waitou.imgloader_lib.HsRoundImageView;
import com.waitou.towards.R;

/**
 * Created by waitou on 17/2/22.
 */

public class CustomCircleImageView extends HsRoundImageView implements SkinCompatSupportable {

    public CustomCircleImageView(Context context) {
        this(context, null);
    }

    public CustomCircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void applySkin() {
        setBorderColor(ThemeUtils.getThemeAttrColor(getContext(), R.attr.colorPrimary));
    }
}
