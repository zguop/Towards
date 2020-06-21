package com.waitou.widget_lib;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by waitou on 17/2/1.
 * viewpager
 */
public class CompatViewPager extends ViewPager {

    //mViewTouchMode表示ViewPager是否全权控制滑动事件，默认为false，即不控制
    private boolean mViewTouchMode = false;

    public CompatViewPager(Context context) {
        this(context, null);
    }

    @SuppressLint("CustomViewStyleable")
    public CompatViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.widget_CompatViewPager);
        if (typedArray != null) {
            try {
                mViewTouchMode = typedArray.getBoolean(R.styleable.widget_CompatViewPager_widget_page_TouchMode, false);
            } finally {
                typedArray.recycle();
            }
        }

        setViewTouchMode(mViewTouchMode);
    }

    public void setViewTouchMode(boolean b) {
        if (b && !isFakeDragging()) {
            //全权控制滑动事件
            beginFakeDrag();
        } else if (!b && isFakeDragging()) {
            //终止控制滑动事件
            endFakeDrag();
        }
        mViewTouchMode = b;
    }

    /**
     * 在mViewTouchMode为true的时候，ViewPager不拦截点击事件，点击事件将由子View处理
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mViewTouchMode) {
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 在mViewTouchMode为true或者滑动方向不是左右的时候，ViewPager将放弃控制点击事件，
     * 这样做有利于在ViewPager中加入ListView等可以滑动的控件，否则两者之间的滑动将会有冲突
     */
    @Override
    public boolean arrowScroll(int direction) {
        if (mViewTouchMode) return false;
        if (direction != FOCUS_LEFT && direction != FOCUS_RIGHT) return false;
        return super.arrowScroll(direction);
    }

}
