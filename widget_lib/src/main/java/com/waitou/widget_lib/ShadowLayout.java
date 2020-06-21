package com.waitou.widget_lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.palette.graphics.Palette;

/**
 * auth aboom
 * date 2019-05-07
 */
public class ShadowLayout extends FrameLayout {

    public static final int ALL    = 0x0f;
    public static final int LEFT   = 0x01;
    public static final int TOP    = 0x02;
    public static final int RIGHT  = 0x04;
    public static final int BOTTOM = 0x08;

    private int   mShadowColor;
    private int   mShadowSide;
    private float mShadow;
    private float mCornerRadius;
    private float mDx;
    private float mDy;

    private boolean isForceInvalidateShadow;
    private boolean isPalette;

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return 0;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return 0;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        isForceInvalidateShadow = !isPalette;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        paletteShadowColor();
        if (isForceInvalidateShadow) {
            isForceInvalidateShadow = false;
            setBackgroundCompat(right - left, bottom - top);
        }
    }

    public void invalidateShadow() {
        isForceInvalidateShadow = true;
        requestLayout();
    }

    public void invalidateShadow(@ColorInt int color) {
        int colorWithAlpha = getColorWithAlpha(color);
        if (mShadowColor != colorWithAlpha) {
            mShadowColor = colorWithAlpha;
            invalidateShadow();
        }
    }

    public int getShadowColor() {
        return mShadowColor;
    }

    public void setPalette(boolean palette) {
        isPalette = palette;
    }

    private void initView(Context context, AttributeSet attrs) {
        initAttributes(context, attrs);
        int xPadding = (int) (mShadow + Math.abs(mDx));
        int yPadding = (int) (mShadow + Math.abs(mDy));
        int left = ((mShadowSide & LEFT) == LEFT) ? xPadding : 0;
        int top = ((mShadowSide & TOP) == TOP) ? yPadding : 0;
        int right = ((mShadowSide & RIGHT) == RIGHT) ? xPadding : 0;
        int bottom = ((mShadowSide & BOTTOM) == BOTTOM) ? yPadding : 0;
        setPadding(left + getPaddingLeft(), top + getPaddingTop(), right + getPaddingRight(), bottom + getPaddingBottom());
    }

    private void setBackgroundCompat(int w, int h) {
        Bitmap bitmap = createShadowBitmap(w, h, mCornerRadius, mShadow, mDx, mDy, mShadowColor);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        setBackground(drawable);
    }

    @SuppressLint("CustomViewStyleable")
    private void initAttributes(Context context, AttributeSet attrs) {
        final TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.widget_ShadowLayout);
        try {
            mCornerRadius = attr.getDimension(R.styleable.widget_ShadowLayout_widget_sl_cornerRadius, getResources().getDimension(R.dimen.widget_default_corner_radius));
            mShadow = attr.getDimension(R.styleable.widget_ShadowLayout_widget_sl_shadow, getResources().getDimension(R.dimen.widget_default_shadow_radius));
            mDx = attr.getDimension(R.styleable.widget_ShadowLayout_widget_sl_dx, 0);
            mDy = attr.getDimension(R.styleable.widget_ShadowLayout_widget_sl_dy, 0);
            mShadowColor = attr.getColor(R.styleable.widget_ShadowLayout_widget_sl_shadowColor, Color.parseColor("#4D757575"));
            isPalette = attr.getBoolean(R.styleable.widget_ShadowLayout_widget_sl_palette, false);
            mShadowSide = attr.getInt(R.styleable.widget_ShadowLayout_widget_sl_side, ALL);
        } finally {
            attr.recycle();
        }
    }

    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight, float cornerRadius, float shadowRadius,
                                      float dx, float dy, int shadowColor) {
        Bitmap output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        RectF shadowRect = new RectF(
                ((mShadowSide & LEFT) == LEFT) ? shadowRadius : 0,
                ((mShadowSide & TOP) == TOP) ? shadowRadius : 0,
                ((mShadowSide & RIGHT) == RIGHT) ? shadowWidth - shadowRadius : shadowWidth,
                ((mShadowSide & BOTTOM) == BOTTOM) ? shadowHeight - shadowRadius : shadowHeight);

        if (dy > 0) {
            shadowRect.top += dy;
            shadowRect.bottom -= dy;
        } else if (dy < 0) {
            shadowRect.top += Math.abs(dy);
            shadowRect.bottom -= Math.abs(dy);
        }

        if (dx > 0) {
            shadowRect.left += dx;
            shadowRect.right -= dx;
        } else if (dx < 0) {
            shadowRect.left += Math.abs(dx);
            shadowRect.right -= Math.abs(dx);
        }

        Paint shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setColor(Color.TRANSPARENT);
        shadowPaint.setStyle(Paint.Style.FILL);

        if (!isInEditMode()) {
            shadowPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor);
        }

        canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint);
        return output;
    }

    /**
     * 获取到柔和的深色的颜色（可传默认值）
     * palette.getDarkMutedColor(Color.BLUE);
     * 获取到活跃的深色的颜色（可传默认值）
     * palette.getDarkVibrantColor(Color.BLUE);
     * 获取到柔和的明亮的颜色（可传默认值）
     * palette.getLightMutedColor(Color.BLUE);
     * 获取到活跃的明亮的颜色（可传默认值）
     * palette.getLightVibrantColor(Color.BLUE);
     * 获取图片中最活跃的颜色（也可以说整个图片出现最多的颜色）（可传默认值）
     * palette.getVibrantColor(Color.BLUE);
     * 获取图片中一个最柔和的颜色（可传默认值）
     * palette.getMutedColor(Color.BLUE);
     */
    private void paletteShadowColor() {
        if (!isPalette) {
            return;
        }
        int childCount = getChildCount();
        if (childCount != 1) {
            return;
        }
        View childAt = getChildAt(0);
        if (childAt instanceof ImageView) {
            Drawable drawable = ((ImageView) childAt).getDrawable();
            if (drawable == null) {
                return;
            }
            int colorWithAlpha = mShadowColor;
            if (drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                Palette.Swatch mutedSwatch = Palette.from(bitmap).generate().getMutedSwatch();
                if (mutedSwatch != null) {
                    colorWithAlpha = getColorWithAlpha(mutedSwatch.getRgb());
                }
            } else if (drawable instanceof ColorDrawable) {
                colorWithAlpha = getColorWithAlpha(((ColorDrawable) drawable).getColor());
            }
            if (mShadowColor != colorWithAlpha) {
                mShadowColor = colorWithAlpha;
                isForceInvalidateShadow = true;
            }
        }
    }

    /**
     * 对色彩加入透明度
     *
     * @return a color with alpha made from color
     */
    private int getColorWithAlpha(int color) {
        int alpha = Color.alpha(color);
        if (alpha < 255) {
            return color;
        }
        int a = Math.min(255, Math.max(0, (int) (0.9f * 255))) << 24;
        int rgb = 0x00ffffff & color;
        return a + rgb;
    }
}