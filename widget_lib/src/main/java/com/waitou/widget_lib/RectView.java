package com.waitou.widget_lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.app.ActivityCompat;

/**
 * Created by waitou on 17/2/8.
 */

public class RectView extends View {

    private float mRadius;
    private Paint mPaint;

    public RectView(Context context) {
        this(context, null);
    }

    public RectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("CustomViewStyleable")
    public RectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.widget_RectView);
        if (typedArray != null) {
            try {
                mRadius = typedArray.getDimension(R.styleable.widget_RectView_widget_radius, 0);
            } finally {
                typedArray.recycle();
            }
        }
        mPaint = new Paint();
    }

    public void setColor(int color) {
        mPaint.setColor(ActivityCompat.getColor(getContext(), color));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mRadius, mRadius, mPaint);
    }
}
