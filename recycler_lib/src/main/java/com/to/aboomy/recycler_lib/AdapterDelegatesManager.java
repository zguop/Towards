package com.to.aboomy.recycler_lib;

import android.util.SparseArray;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * auth aboom
 * date 2019-05-06
 */
public class AdapterDelegatesManager {
    protected SparseArray<AdapterDelegate> adapterDelegates = new SparseArray<>();
    protected AdapterDelegate              adapterDelegate;

    public int getViewType(Displayable displayable) {
        for (int i = 0; i < adapterDelegates.size(); i++) {
            AdapterDelegate adapterDelegate = adapterDelegates.valueAt(i);
            if (adapterDelegate.isForViewType(displayable)) {
                return adapterDelegates.keyAt(i);
            }
        }
        return -0xff;
    }

    public int addDelegate(AdapterDelegate adapterDelegate) {
        int viewType = adapterDelegates.size();
        while (adapterDelegates.get(viewType) != null) {
            viewType++;
        }
        adapterDelegates.put(viewType, adapterDelegate);
        return viewType;
    }

    public void convert(BaseViewHolder helper, Displayable item, int position) {
        AdapterDelegate adapterDelegate = adapterDelegates.get(helper.getItemViewType());
        adapterDelegate.context = helper.itemView.getContext();
        adapterDelegate.convert(helper, item, position);
    }
}
