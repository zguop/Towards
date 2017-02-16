package com.waitou.wt_library.view.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.waitou.wt_library.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xiayong on 2015/9/29.
 */
public class CircleIndicator extends View {
    private ViewPager viewPager;
    private int       countSize;

    private List<ShapeHolder> tabItems;
    private ShapeHolder       movingItem;

    //config list
    private int     mCurItemPosition;
    private float   mCurItemPositionOffset;
    private float   mIndicatorRadius;
    private float   mIndicatorMargin;
    private int     mIndicatorBackground;
    private int     mIndicatorSelectedBackground;
    private Gravity mIndicatorLayoutGravity;
    private Mode    mIndicatorMode;

    //default value
    private final int DEFAULT_INDICATOR_RADIUS              = 10;
    private final int DEFAULT_INDICATOR_MARGIN              = 40;
    private final int DEFAULT_INDICATOR_BACKGROUND          = Color.BLUE;
    private final int DEFAULT_INDICATOR_SELECTED_BACKGROUND = Color.RED;
    private final int DEFAULT_INDICATOR_LAYOUT_GRAVITY      = Gravity.CENTER.ordinal();
    private final int DEFAULT_INDICATOR_MODE                = Mode.SOLO.ordinal();
    private boolean isRight;

    public enum Gravity {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum Mode {
        INSIDE,
        OUTSIDE,
        SOLO
    }

    public CircleIndicator(Context context) {
        this(context, null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        tabItems = new ArrayList<>();
        handleTypedArray(context, attrs);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
        mIndicatorRadius = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_wt_radius, DEFAULT_INDICATOR_RADIUS);
        mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_wt_margin, DEFAULT_INDICATOR_MARGIN);
        mIndicatorBackground = typedArray.getColor(R.styleable.CircleIndicator_wt_background, DEFAULT_INDICATOR_BACKGROUND);
        mIndicatorSelectedBackground = typedArray.getColor(R.styleable.CircleIndicator_wt_selected_background, DEFAULT_INDICATOR_SELECTED_BACKGROUND);
        int gravity = typedArray.getInt(R.styleable.CircleIndicator_wt_gravity, DEFAULT_INDICATOR_LAYOUT_GRAVITY);
        mIndicatorLayoutGravity = Gravity.values()[gravity];
        int mode = typedArray.getInt(R.styleable.CircleIndicator_wt_mode, DEFAULT_INDICATOR_MODE);
        mIndicatorMode = Mode.values()[mode];
        typedArray.recycle();
    }

    public void setViewPager(final ViewPager viewPager, boolean canLoop) {
        this.viewPager = viewPager;
        countSize = viewPager.getAdapter().getCount();
        if (canLoop) {
            countSize = countSize / 300;
        }
        createTabItems();
        createMovingItem();
        setUpListener();
    }

    public int getCountSize() {
        return countSize;
    }

    private void setUpListener() {
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (mIndicatorMode != Mode.SOLO) {
                    trigger(toRealPosition(position), positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (mIndicatorMode == Mode.SOLO) {
                    trigger(toRealPosition(position), 0);
                }
            }
        });
    }

    public int toRealPosition(int position) {
        if (countSize != 0) {
            return position % countSize;
        }
        return 0;
    }

    /**
     * trigger to redraw the indicator when the ViewPager's selected item changed!
     */
    private void trigger(int position, float positionOffset) {
        isRight = positionOffset > mCurItemPositionOffset;
        CircleIndicator.this.mCurItemPosition = position;
        CircleIndicator.this.mCurItemPositionOffset = positionOffset;
        requestLayout();
    }

    private void createTabItems() {
        for (int i = 0; i < countSize; i++) {
            OvalShape circle = new OvalShape();
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            Paint paint = drawable.getPaint();
            paint.setColor(mIndicatorBackground);
            paint.setAntiAlias(true);
            shapeHolder.setPaint(paint);
            tabItems.add(shapeHolder);
        }
    }

    private void createMovingItem() {
        OvalShape circle = new OvalShape();
        ShapeDrawable drawable = new ShapeDrawable(circle);
        movingItem = new ShapeHolder(drawable);
        Paint paint = drawable.getPaint();
        paint.setColor(mIndicatorSelectedBackground);
        paint.setAntiAlias(true);

        switch (mIndicatorMode) {
            case INSIDE:
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
                break;
            case OUTSIDE:
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                break;
            case SOLO:
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                break;
        }

        movingItem.setPaint(paint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final int width = getWidth();
        final int height = getHeight();
        layoutTabItems(width, height);
        layoutMovingItem(mCurItemPosition, mCurItemPositionOffset);
    }

    private void layoutTabItems(final int containerWidth, final int containerHeight) {
        if (tabItems == null) {
            throw new IllegalStateException("forget to create tabItems?");
        }
        final float yCoordinate = containerHeight * 0.5f;
        final float startPosition = startDrawPosition(containerWidth);
        for (int i = 0; i < tabItems.size(); i++) {
            ShapeHolder item = tabItems.get(i);
            item.resizeShape(2 * mIndicatorRadius, 2 * mIndicatorRadius);
            item.setY(yCoordinate - mIndicatorRadius);
            float x = startPosition + (mIndicatorMargin + mIndicatorRadius * 2) * i;
            item.setX(x);
        }
    }

    private float startDrawPosition(final int containerWidth) {
        if (mIndicatorLayoutGravity == Gravity.LEFT)
            return 0;
        float tabItemsLength = tabItems.size() * (2 * mIndicatorRadius + mIndicatorMargin) - mIndicatorMargin;
        if (containerWidth < tabItemsLength) {
            return 0;
        }
        if (mIndicatorLayoutGravity == Gravity.CENTER) {
            return (containerWidth - tabItemsLength) / 2;
        }
        return containerWidth - tabItemsLength;
    }

    private void layoutMovingItem(final int position, final float positionOffset) {

        if (movingItem == null) {
            throw new IllegalStateException("forget to create movingItem?");
        }

        if (tabItems.size() == 0) {
            return;
        }

        ShapeHolder item = tabItems.get(position);
        movingItem.resizeShape(item.getWidth(), item.getHeight());

        float x = item.getX() + (mIndicatorMargin + mIndicatorRadius * 2) * positionOffset;

        if (x > tabItems.get(tabItems.size() - 1).getX()) {
            if (isRight) {
                if (positionOffset > .5) {
                    x = tabItems.get(0).getX();
                } else {
                    x = tabItems.get(tabItems.size() - 1).getX();
                }
            } else {
                if (positionOffset < .5) {
                    x = tabItems.get(tabItems.size() - 1).getX();
                } else {
                    x = tabItems.get(0).getX();
                }
            }
        }

        movingItem.setX(x);
        movingItem.setY(item.getY());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        for (ShapeHolder item : tabItems) {
            drawItem(canvas, item);
        }


        if (movingItem != null) {
            drawItem(canvas, movingItem);
        }
        canvas.restoreToCount(sc);
    }

    private void drawItem(Canvas canvas, ShapeHolder shapeHolder) {
        canvas.save();
        canvas.translate(shapeHolder.getX(), shapeHolder.getY());
        shapeHolder.getShape().draw(canvas);
        canvas.restore();
    }

    public void setIndicatorRadius(float indicatorRadius) {
        this.mIndicatorRadius = indicatorRadius;
    }

    public void setIndicatorMargin(float indicatorMargin) {
        this.mIndicatorMargin = indicatorMargin;
    }

    public void setIndicatorBackground(int indicatorBackground) {
        this.mIndicatorBackground = indicatorBackground;
    }

    public void setIndicatorSelectedBackground(int indicatorSelectedBackground) {
        mIndicatorBackground = indicatorSelectedBackground;
        if (movingItem.getPaint() != null) {
            movingItem.getPaint().setColor(ActivityCompat.getColor(getContext(), indicatorSelectedBackground));
            requestLayout();
        }
    }

    public void setIndicatorLayoutGravity(Gravity mIndicatorLayoutGravity) {
        this.mIndicatorLayoutGravity = mIndicatorLayoutGravity;
    }

    public void setIndicatorMode(Mode mIndicatorMode) {
        this.mIndicatorMode = mIndicatorMode;
    }

    private class ShapeHolder {
        private float x = 0, y = 0;
        private ShapeDrawable shape;
        private int           color;
        private float alpha = 1f;
        private Paint paint;

        public void setPaint(Paint value) {
            paint = value;
        }

        public Paint getPaint() {
            return paint;
        }

        public void setX(float value) {
            x = value;
        }

        public float getX() {
            return x;
        }

        public void setY(float value) {
            y = value;
        }

        public float getY() {
            return y;
        }

        public void setShape(ShapeDrawable value) {
            shape = value;
        }

        public ShapeDrawable getShape() {
            return shape;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int value) {
            shape.getPaint().setColor(value);
            color = value;

        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
            shape.setAlpha((int) ((alpha * 255f) + .5f));
        }

        public float getWidth() {
            return shape.getShape().getWidth();
        }

        public void setWidth(float width) {
            Shape s = shape.getShape();
            s.resize(width, s.getHeight());
        }

        public float getHeight() {
            return shape.getShape().getHeight();
        }

        public void setHeight(float height) {
            Shape s = shape.getShape();
            s.resize(s.getWidth(), height);
        }

        public void resizeShape(final float width, final float height) {
            shape.getShape().resize(width, height);
        }

        public ShapeHolder(ShapeDrawable s) {
            shape = s;
        }
    }
}
