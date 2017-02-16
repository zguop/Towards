package com.waitou.wt_library.view.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waitou on 17/2/12.
 */

public abstract class WTPagerAdapter<T> extends PagerAdapter {

    private static final int MULTIPLE_COUNT = 300;

    protected List<T>           mData = new ArrayList<>();
    private   SparseArray<View> mViews;
    private   WTViewPager       mViewPager;
    private   boolean           isCanLoop;

    WTPagerAdapter(List<T> data) {
        this.mData = data;
        mViews = new SparseArray<>();
    }

    @Override
    public int getCount() {
        return isCanLoop ? getRealCount() * MULTIPLE_COUNT : getRealCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = toRealPosition(position);
        View view = mViews.get(realPosition);
        if (view == null) {
            view = newView(container.getContext(), realPosition);
            mViews.put(realPosition, view);
        }
        if (view != null) {
            container.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = mViewPager.getCurrentItem();
        if (position == 0) {
            position = mViewPager.getFirstItem();
        } else if (position == getCount() - 1) {
            position = mViewPager.getLastItem();
        }
        if (mViewPager.getCurrentItem() != position) {
            mViewPager.setCurrentItem(position, false);
        }
    }

    public void setCanLoop(boolean canLoop) {
        isCanLoop = canLoop;
    }

    public boolean isCanLoop() {
        return isCanLoop;
    }

    public void setViewPager(WTViewPager viewPager) {
        mViewPager = viewPager;
    }

    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount != 0) {
            return position % realCount;
        }
        return 0;
    }

    public int getRealCount() {
        return mData == null ? 0 : mData.size();
    }

    protected abstract View newView(Context context, int realPosition);
}
