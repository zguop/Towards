package com.to.aboomy.recycler_lib.delegate;

import android.util.SparseArray;

import com.chad.library.adapter.base.BaseViewHolder;
import com.to.aboomy.recycler_lib.adapter.Displayable;

/**
 * auth aboom
 * date 2019-05-06
 */
public class AdapterDelegatesManager {
    private SparseArray<AdapterDelegate> adapterDelegates = new SparseArray<>();

    public int getViewType(Displayable displayable) {
        for (int i = 0; i < adapterDelegates.size(); i++) {
            AdapterDelegate adapterDelegate = adapterDelegates.valueAt(i);
            if (adapterDelegate.isForViewType(displayable)) {
                return adapterDelegates.keyAt(i);
            }
        }
        int viewType = -0xff;
        Class[] holderClass = displayable.getHolderClass();
        if (holderClass != null) {
            for (Class aClass : holderClass) {
                AdapterDelegate adapterDelegate;
                try {
                    adapterDelegate = (AdapterDelegate) aClass.newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException("Please set a non-parameter and public constructor for " + aClass.getSimpleName());
                } catch (ClassCastException e) {
                    throw new RuntimeException("Please make sure " + aClass.getSimpleName() + " extends AdapterDelegate");
                }

                int type = addDelegate(adapterDelegate);
                if (adapterDelegate.isForViewType(displayable)) {
                    viewType = type;
                }
            }
        }
        return viewType;
    }

    public int getLayoutId(int viewType) {
        return adapterDelegates.get(viewType).layout();
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
        adapterDelegate.convert(helper, item, position);
    }

    public void createViewHolder(BaseViewHolder helper, int viewType) {
        AdapterDelegate adapterDelegate = adapterDelegates.get(viewType);
        adapterDelegate.context = helper.itemView.getContext();
        adapterDelegate.viewHolderCreated(helper);
    }
}
