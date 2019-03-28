package com.to.aboomy.recycler_lib;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * auth aboom
 * date 2018/7/9
 */
public class QyRecyclerAdapter extends MultipleItemRvAdapter<Displayable, BindingViewHolder> {

    private List<QyItemProvider> mProviderList = new ArrayList<>();
    private IQyPresenter mQyPresenter;

    public QyRecyclerAdapter() {
        super(null);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return binding.getRoot();
    }

    @Override
    protected BindingViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BindingViewHolder bindingViewHolder = super.onCreateDefViewHolder(parent, viewType);
        bindingViewHolder.setQyHelper(mQyPresenter);
        return bindingViewHolder;
    }

    @Override
    protected void convert(BindingViewHolder helper, Displayable item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.item, item);
        binding.setVariable(BR.presenter, mQyPresenter);
        super.convert(helper, item);
        helper.getBinding().executePendingBindings();
    }

    @Override
    protected int getViewType(Displayable displayable) {
        return displayable.getItemType();
    }

    @Override
    public void registerItemProvider() {
        for (QyItemProvider qyItemProvider : mProviderList) {
            mProviderDelegate.registerProvider(qyItemProvider);
        }
    }

    public void addProvider(QyItemProvider... qyItemProviders) {
        Collections.addAll(mProviderList, qyItemProviders);
        finishInitialize();
    }

    public void setQyPresenter(IQyPresenter qyPresenter) {
        mQyPresenter = qyPresenter;
    }
}
