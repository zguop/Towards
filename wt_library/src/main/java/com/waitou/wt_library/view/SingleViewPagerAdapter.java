package com.waitou.wt_library.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;

import com.to.aboomy.banner.QyPagerAdapter;
import com.waitou.wt_library.BR;

import java.util.List;

import static com.waitou.wt_library.BR.position;

/**
 * Created by waitou on 17/2/15.
 */

public class SingleViewPagerAdapter<T> extends QyPagerAdapter<T> {

    private int            mLayoutRes;
    private LayoutInflater mLayoutInflater;
    private Presenter      mPresenter;
    private Decorator      mDecorator;

    public interface Presenter {
    }

    public interface Decorator {
        void decorator(ViewDataBinding binding, int position);
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
        inflate.setVariable(position, realPosition);
        if (mDecorator != null) {
            mDecorator.decorator(inflate, realPosition);
        }
        return inflate.getRoot();
    }

    public void setDecorator(Decorator decorator) {
        mDecorator = decorator;
    }

    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    private int getLayoutRes() {
        return mLayoutRes;
    }

}
