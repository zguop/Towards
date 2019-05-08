package com.to.aboomy.recycler_lib;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

/**
 * auth aboom
 * date 2019-05-06
 */
public class MultipleRecyclerAdapter extends BaseQuickAdapter<Displayable, BindingViewHolder> {

    protected AdapterDelegatesManager delegatesManager;

    public MultipleRecyclerAdapter() {
        super(null);
        delegatesManager = new AdapterDelegatesManager();
        //设置当前item的ViewType
        setMultiTypeDelegate(new MultiTypeDelegate<Displayable>() {
            @Override
            protected int getItemType(Displayable displayable) {
                return delegatesManager.getViewType(displayable);
            }
        });
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = null;
        try {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutResId, parent, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return binding.getRoot();
    }

    @Override
    protected void convert(BindingViewHolder helper, Displayable item) {
        ViewDataBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setVariable(BR.item, item);
            helper.getBinding().executePendingBindings();
        }
        int position = helper.getLayoutPosition() - getHeaderLayoutCount();
        delegatesManager.convert(helper, item, position);
    }

    public void addDelegate(AdapterDelegate... adapterDelegates) {
        for (AdapterDelegate adapterDelegate : adapterDelegates) {
            addDelegate(adapterDelegate);
        }
    }

    public void addDelegate(AdapterDelegate adapterDelegate) {
        int viewType = delegatesManager.addDelegate(adapterDelegate);
        getMultiTypeDelegate().registerItemType(viewType, adapterDelegate.layout());
    }
}
