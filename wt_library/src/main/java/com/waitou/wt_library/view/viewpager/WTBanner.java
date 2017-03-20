package com.waitou.wt_library.view.viewpager;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.waitou.wt_library.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;


/**
 * Created by waitou on 17/2/12.
 * 轮播控件
 */

public class WTBanner extends RelativeLayout {

    private boolean canLoop = false;
    private WTViewPager     mViewPager;
    private CircleIndicator mCircleIndicator;
    private boolean         turning; //是否正在翻页
    private long autoTurningTime = 4_000;
    private AdSwitchTask mAdSwitchTask;
    private boolean      isSetAdapter;


    public WTBanner(Context context) {
        this(context, null);
    }

    public WTBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WTBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initViewPagerScroll();
        mAdSwitchTask = new AdSwitchTask(this);
    }

    private void init(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.view_banner, this, true);
        mViewPager = (WTViewPager) rootView.findViewById(R.id.viewpager);
        mCircleIndicator = (CircleIndicator) rootView.findViewById(R.id.indicator);
    }

    public void setAdapter(PagerAdapter adapter) {
        if (adapter == null || isSetAdapter) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setAdapter(adapter);
            canLoop = mViewPager.isCanLoop();
            if (canLoop) {
                startTurning();
            }
            showIndicators(canLoop);
            if (mCircleIndicator != null) {
                mCircleIndicator.setViewPager(mViewPager, canLoop);
            }
        }

        this.isSetAdapter = true;
    }

    public void showIndicators(boolean flag) {
        if (mCircleIndicator != null) {
            if (flag) {
                mCircleIndicator.setVisibility(VISIBLE);
            } else {
                mCircleIndicator.setVisibility(GONE);
            }
        }
    }

    public void startTurning() {
        if (turning) {
            stopTurning();
        }
        turning = true;
        postDelayed(mAdSwitchTask, autoTurningTime);
    }

    public void stopTurning() {
        turning = false;
        removeCallbacks(mAdSwitchTask);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (canLoop) {
            stopTurning();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (canLoop) {
            startTurning();
        }
    }

    private void initViewPagerScroll() {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(
                    mViewPager.getContext());
            mScroller.set(mViewPager, scroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static class AdSwitchTask implements Runnable {

        private final WeakReference<WTBanner> reference;

        AdSwitchTask(WTBanner convenientBanner) {
            this.reference = new WeakReference<>(convenientBanner);
        }

        @Override
        public void run() {
            WTBanner convenientBanner = reference.get();
            if (convenientBanner != null) {
                if (convenientBanner.mViewPager != null && convenientBanner.turning) {
                    int page = convenientBanner.mViewPager.getCurrentItem() + 1;
                    convenientBanner.mViewPager.setCurrentItem(page);
                    if (convenientBanner.mCircleIndicator.getCountSize() == 0) {
                        convenientBanner.showIndicators(convenientBanner.canLoop);
                        convenientBanner.mCircleIndicator.setViewPager(convenientBanner.mViewPager, convenientBanner.canLoop);
                    }
                    convenientBanner.postDelayed(convenientBanner.mAdSwitchTask, convenientBanner.autoTurningTime);
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (canLoop && action == MotionEvent.ACTION_DOWN) {
            stopTurning();
        }
        if (canLoop && action == MotionEvent.ACTION_UP) {
            startTurning();
        }

        if (canLoop && action == MotionEvent.ACTION_CANCEL) {
            startTurning();
        }
        return super.dispatchTouchEvent(ev);
    }
}
