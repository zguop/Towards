package com.waitou.widget_lib.search;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;


/**
 * auth aboom
 * date 2018/8/23
 */
public class DrawableCenterEditText extends AppCompatEditText {

    private boolean defaultCenter = true;

    public DrawableCenterEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DrawableCenterEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableCenterEditText(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (defaultCenter && !hasFocus()) {
            Drawable[] drawables = getCompoundDrawables();
            Drawable drawableLeft = drawables[0];
            translate(canvas, drawableLeft);
            Drawable drawableRight = drawables[2];
            translate(canvas, drawableRight);
        }
        super.onDraw(canvas);
    }

    public void setCenter(boolean defaultCenter) {
        this.defaultCenter = defaultCenter;
    }

    private void translate(Canvas canvas, Drawable drawable) {
        if (drawable != null) {
            float textWidth = 0f;
            CharSequence measureText = TextUtils.isEmpty(getText()) ? getHint() : getText();
            if (!TextUtils.isEmpty(measureText)) {
                textWidth = getPaint().measureText(measureText.toString());
            }
            int drawablePadding = getCompoundDrawablePadding();
            int drawableWidth = drawable.getIntrinsicWidth();
            int padding = getPaddingStart() + getPaddingEnd();
            float bodyWidth = textWidth + drawableWidth + drawablePadding + padding;
            canvas.translate((getWidth() - bodyWidth) / 2, 0);
        }
    }
}
