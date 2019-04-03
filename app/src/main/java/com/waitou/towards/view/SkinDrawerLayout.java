package com.waitou.towards.view;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;

import com.to.aboomy.theme_lib.skin.SkinCompatSupportable;
import com.waitou.wt_library.base.BaseActivity;

/**
 * Created by waitou on 17/3/5.
 */

public class SkinDrawerLayout extends DrawerLayout implements SkinCompatSupportable {

    public SkinDrawerLayout(Context context) {
        this(context, null);
    }

    public SkinDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void applySkin() {
        if (getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).transparencyBar(this);
        }
    }
}
