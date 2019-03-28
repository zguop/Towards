package com.to.aboomy.recycler_lib;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * auth aboom
 * date 2018/7/15
 */
public class BindingViewHolder<T extends ViewDataBinding> extends BaseViewHolder {

    private IQyPresenter mQyPresenter;

    public BindingViewHolder(View view) {
        super(view);
    }

    @SuppressWarnings("unchecked")
    public T getBinding() {
        return (T) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
    }

    public IQyPresenter getQyPresenter() {
        return mQyPresenter;
    }

    void setQyHelper(IQyPresenter iQyPresenter) {
        this.mQyPresenter = iQyPresenter;
    }


}