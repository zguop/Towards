package com.to.aboomy.recycler_lib.species;


import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.to.aboomy.recycler_lib.adapter.Displayable;
import com.to.aboomy.recycler_lib.delegate.AdapterDelegate;

/**
 * auth aboom
 * date 2019-11-07
 */
public class ShowDelegate extends AdapterDelegate {

    private int layoutId;

    @Override
    public boolean isForViewType(@NonNull Displayable displayable) {
        if (displayable instanceof Show) {
            layoutId = ((Show) displayable).getLayoutId();
            return true;
        }
        return false;
    }

    @Override
    public int layout() {
        return layoutId;
    }

    @Override
    public void convert(BaseViewHolder helper, Displayable data, int position) {}
}
