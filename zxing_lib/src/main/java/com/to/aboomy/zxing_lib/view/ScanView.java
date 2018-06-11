package com.to.aboomy.zxing_lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.to.aboomy.zxing_lib.R;

/**
 * auth aboom
 * date 2018/6/10
 */
public class ScanView extends FrameLayout {

    private int maskColor;
    private int lineColir;

    private Paint mPaint;
    private int   mLineWith;
    private int   mCornerWidth;


    public ScanView(@NonNull Context context) {
        this(context, null);
    }

    public ScanView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setWillNotDraw(false);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScanView);
        maskColor = typedArray.getColor(R.styleable.ScanView_maskColor, Color.parseColor("#60000000"));
        lineColir = typedArray.getColor(R.styleable.ScanView_lineColor, Color.parseColor("#e0e2e4"));
        typedArray.recycle();
        mPaint = new Paint();



        //边框线的长度
        mLineWith = dip2px(20);
        //边框线的厚度
        mCornerWidth = dip2px(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Log.e("aa", "canvas width = " + width + "canvas height = " + height + " width = " + getWidth() + " height = " + height);
        mPaint.setColor(maskColor);
        canvas.drawRect(0, 0, width, height, mPaint);
        mPaint.setColor(lineColir);
        int centerWidth = getWidth() / 4;
        int centerHeight = getHeight() / 4;
        int left = centerWidth - mLineWith;
        int top = centerHeight / 2 - mLineWith;
        int right = centerWidth / 2 + mLineWith;
        int bottom = centerHeight / 2 + mLineWith;


        canvas.drawRect(centerWidth, centerHeight, centerWidth +mLineWith, centerHeight + mCornerWidth, mPaint);
        canvas.drawRect(centerWidth,centerHeight ,centerWidth + mCornerWidth , centerHeight + mLineWith,mPaint);

    }

    public int dip2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }
}
