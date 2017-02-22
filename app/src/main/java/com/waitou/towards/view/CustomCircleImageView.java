package com.waitou.towards.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.waitou.towards.R;
import com.waitou.wt_library.theme.SkinCompatSupportable;
import com.waitou.wt_library.theme.ThemeUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by waitou on 17/2/22.
 */

public class CustomCircleImageView extends CircleImageView implements SkinCompatSupportable {

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
        setBorderColor(Color.BLACK);
        setBorderColor(ThemeUtils.getThemeAttrColor(getContext(), R.attr.colorPrimary));
    }
}
