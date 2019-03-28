package com.waitou.towards.view;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by waitou on 17/2/21.
 */

public class NestFlexboxLayout extends FlexboxLayout {

    public NestFlexboxLayout(Context context) {
        this(context, null);
    }

    public NestFlexboxLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestFlexboxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
