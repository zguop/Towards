package com.to.aboomy.recycler_lib.adapter;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.to.aboomy.recycler_lib.delegate.AdapterDelegate;
import com.to.aboomy.recycler_lib.delegate.AdapterDelegatesManager;

/**
 * auth aboom
 * date 2019-05-06
 */
public class MultipleAdapter extends BaseQuickAdapter<Displayable, BaseViewHolder> {

    protected AdapterDelegatesManager delegatesManager;

    public MultipleAdapter() {
        super(null);
        delegatesManager = new AdapterDelegatesManager();
    }

    @Override
    protected int getDefItemViewType(int position) {
        return delegatesManager.getViewType(mData.get(position));
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = super.createBaseViewHolder(parent, delegatesManager.getLayoutId(viewType));
        delegatesManager.createViewHolder(baseViewHolder, viewType);
        return baseViewHolder;
    }

    @Override
    protected void convert(BaseViewHolder helper, Displayable item) {
        int position = helper.getLayoutPosition() - getHeaderLayoutCount();
        delegatesManager.convert(helper, item, position);
    }

    public void addDelegate(AdapterDelegate... adapterDelegates) {
        for (AdapterDelegate adapterDelegate : adapterDelegates) {
            addDelegate(adapterDelegate);
        }
    }

    public void addDelegate(AdapterDelegate adapterDelegate) {
        delegatesManager.addDelegate(adapterDelegate);
    }
}
