package cn.bertsir.zbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.R;


/**
 * Created by Bert on 2017/9/20.
 */

public class ScanView extends View {

    private int maskColor;
    private int lineColor;

    private Paint mPaint;
    private int   mLineWith;
    private int   mCornerWidth;

    private int scanWidth;
    private int scanHeight;

    public ScanView(@NonNull Context context) {
        this(context, null);
    }

    public ScanView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScanView);
        maskColor = typedArray.getColor(R.styleable.ScanView_maskColor, Color.parseColor("#60000000"));
        lineColor = typedArray.getColor(R.styleable.ScanView_lineColor, Color.parseColor("#e0e2e4"));
        int scanType = typedArray.getInt(R.styleable.ScanView_scanType, QrConfig.SCANVIEW_TYPE_QRCODE);
        typedArray.recycle();
        switch (scanType) {
            case QrConfig.SCANVIEW_TYPE_QRCODE:
                scanWidth = dip2px(200);
                scanHeight = dip2px(200);
                break;
            case QrConfig.SCANVIEW_TYPE_BARCODE:
                scanWidth = dip2px(300);
                scanHeight = dip2px(100);
                break;
            default:
        }
        //边框线的长度
        mLineWith = dip2px(20);
        //边框线的厚度
        mCornerWidth = dip2px(2);
        mPaint = new Paint();


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
        int centerWidth = width / 2;
        int centerHeight = height / 2;
        int left = centerWidth - scanWidth / 2;
        int top = centerHeight - scanHeight / 2;
        int right = centerWidth + scanWidth / 2;
        int bottom = centerHeight + scanHeight / 2;
        mPaint.setColor(maskColor);

        canvas.drawRect(0, 0, width, top, mPaint);
        canvas.drawRect(0, top, left, bottom + 1, mPaint);
        canvas.drawRect(right + 1, top, width, bottom + 1, mPaint);
        canvas.drawRect(0, bottom + 1, width, height, mPaint);
        mPaint.setColor(lineColor);

        canvas.drawRect(left, top, left + mLineWith, top + mCornerWidth, mPaint);
        canvas.drawRect(left, top, left + mCornerWidth, top + mLineWith, mPaint);

        canvas.drawRect(right - mLineWith, top, right, top + mCornerWidth, mPaint);
        canvas.drawRect(right - mCornerWidth, top, right, top + mLineWith, mPaint);

        canvas.drawRect(left, bottom - mCornerWidth, left + mLineWith, bottom, mPaint);
        canvas.drawRect(left, bottom - mLineWith, left + mCornerWidth, bottom, mPaint);

        canvas.drawRect(right - mLineWith, bottom - mCornerWidth, right, bottom, mPaint);
        canvas.drawRect(right - mCornerWidth, bottom - mLineWith, right, bottom, mPaint);
    }

    public int dip2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }
}
