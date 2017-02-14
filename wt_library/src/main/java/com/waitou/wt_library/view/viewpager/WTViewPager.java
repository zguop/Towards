package com.waitou.wt_library.view.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by waitou on 17/2/12.
 */

public class WTViewPager extends ViewPager {

    private OnPageChangeListener mOuterPageChangeListener;

    private WTPagerAdapter mAdapter;

    public WTViewPager(Context context) {
        this(context, null);
    }

    public WTViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.addOnPageChangeListener(mOnPageChangeListener);
    }

    public void setAdapter(PagerAdapter adapter) {
        mAdapter = (WTPagerAdapter) adapter;
        mAdapter.setCanLoop(mAdapter.getRealCount() != 1);
        mAdapter.setViewPager(this);
        super.setAdapter(mAdapter);
        setCurrentItem(getFirstItem(), false);
    }

    public int getFirstItem() {
        return mAdapter.isCanLoop() ? mAdapter.getRealCount() : 0;
    }

    public int getLastItem() {
        return mAdapter.getRealCount() - 1;
    }

    public boolean isCanLoop(){
        return mAdapter.isCanLoop();
    }

    public void addPageChangeListener(OnPageChangeListener listener) {
        this.mOuterPageChangeListener = listener;
    }

    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = mAdapter.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                if (mOuterPageChangeListener != null) {
                    mOuterPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mOuterPageChangeListener != null) {
                if (position != mAdapter.getRealCount() - 1) {
                    mOuterPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > .5) {
                        mOuterPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOuterPageChangeListener.onPageScrolled(position, 0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOuterPageChangeListener != null) {
                mOuterPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };
}
