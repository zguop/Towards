package cn.bertsir.zbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import cn.bertsir.zbar.Qr.Symbol;
import cn.bertsir.zbar.camera.QrConfig;
import cn.bertsir.zbar.R;

public class ScanView extends View {

    /**
     * 提示文字标题
     */
    private String scanTitle;

    /**
     * 提示文字描述
     */
    private String scanTips;

    /**
     * 文字大小
     */
    private float scanTitleSize;
    private float scanTipsSize;

    /**
     * 背景色
     */
    private int maskColor;

    /**
     * 边框颜色
     */
    private int lineColor;

    /**
     * 绘制边框的笔
     */
    private Paint mPaint;

    /**
     * 绘制文字的笔
     */
    private TextPaint mTextPaint;

    /**
     * 扫码的宽高
     */
    private int scanWidth;
    private int scanHeight;

    private int mLineWith;
    private int mCornerWidth;


    public ScanView(@NonNull Context context) {
        this(context, null);
    }

    public ScanView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScanView);
        maskColor = typedArray.getColor(R.styleable.ScanView_qr_maskColor, Color.parseColor("#60000000"));
        lineColor = typedArray.getColor(R.styleable.ScanView_qr_lineColor, Color.parseColor("#e0e2e4"));
        scanTitle = typedArray.getString(R.styleable.ScanView_qr_scan_title);
        scanTips = typedArray.getString(R.styleable.ScanView_qr_scan_tips);
        scanTitleSize = typedArray.getDimension(R.styleable.ScanView_qr_scan_title_size, dip2px(16));
        scanTipsSize = typedArray.getDimension(R.styleable.ScanView_qr_scan_tips_size, dip2px(12));
        setScanType(typedArray.getInt(R.styleable.ScanView_qr_scanType, QrConfig.SCANVIEW_TYPE_QRCODE), false);
        typedArray.recycle();
        //边框线的长度
        mLineWith = dip2px(20);
        //边框线的厚度
        mCornerWidth = dip2px(2);
        mPaint = new Paint();
        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.WHITE);

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

        if (!TextUtils.isEmpty(scanTitle)) {
            mTextPaint.setTextSize(scanTitleSize);
            float titleScreen = mTextPaint.measureText(scanTitle) / 2;
            canvas.drawText(scanTitle, centerWidth - titleScreen, bottom + scanTitleSize + dip2px(16), mTextPaint);
        }

        if (!TextUtils.isEmpty(scanTips)) {
            mTextPaint.setTextSize(scanTipsSize);
            char[] chars = scanTips.toCharArray();
            float textY = bottom + scanTitleSize + dip2px(16) + dip2px(8) + scanTipsSize;
            drawTipsText(0, width - dip2px(100), centerWidth, textY, chars, scanTips, canvas, mTextPaint);
        }
    }

    private void drawTipsText(int start, int totalWidth, float x, float textY, char[] chars, String text, Canvas canvas, Paint paint) {
        int end = 0;
        int w = 0;
        for (int i = start; i < chars.length; i++) {
            float textScreen = mTextPaint.measureText(String.valueOf(chars[i]));
            if (w > totalWidth) {
                end = i;
                break;
            }
            if (i == chars.length - 1) {
                end = chars.length;
            }
            w += textScreen;
        }
        float textX = x - w / 2;
        canvas.drawText(text, start, end, textX, textY, paint);
        if (end != chars.length) {
            drawTipsText(end, totalWidth, x, textY + scanTipsSize + dip2px(4), chars, text, canvas, paint);
        }
    }

    public void startScan() {
        post(() -> {
            Symbol.cropWidth = scanWidth;
            Symbol.cropHeight = scanHeight;
            Symbol.screenWidth = getWidth();
            Symbol.screenHeight = getHeight();
        });
    }

    public void setScanTitle(String scanTitle) {
        this.scanTitle = scanTitle;
        invalidate();
    }

    public void setScanTips(String scanTips) {
        this.scanTips = scanTips;
        invalidate();
    }

    public void setScanType(int scanType, boolean isInvalidate) {
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
        if (isInvalidate) {
            invalidate();
        }
    }

    private int dip2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }
}
