package com.waitou.wt_library.view.viewpager;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;

import com.waitou.wt_library.BR;

import java.util.List;

/**
 * Created by waitou on 17/2/15.
 */

public class SingleViewPagerAdapter<T> extends WTPagerAdapter<T> {

    private int            mLayoutRes;
    private LayoutInflater mLayoutInflater;
    private Presenter      mPresenter;

    public interface Presenter {
    }

    public SingleViewPagerAdapter(Context context, List<T> data, int layoutRes) {
        super(data);
        mLayoutRes = layoutRes;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    protected View newView(Context context, int realPosition) {
        ViewDataBinding inflate = DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(), null, false);
        inflate.setVariable(BR.item, mData.get(realPosition));
        inflate.setVariable(BR.presenter, mPresenter);
        inflate.setVariable(BR.position, realPosition);
        return inflate.getRoot();
    }

    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    private int getLayoutRes() {
        return mLayoutRes;
    }

    public void set(List<T> viewModels) {
        mData.clear();
        addAll(viewModels);
    }

    public void addAll(List<T> viewModels) {
        mData.addAll(viewModels);
        notifyDataSetChanged();
    }
}
