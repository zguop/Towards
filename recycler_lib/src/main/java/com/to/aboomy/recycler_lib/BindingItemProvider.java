package com.to.aboomy.recycler_lib;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * auth aboom
 * date 2019/3/31
 */
public abstract class BindingItemProvider extends BaseItemProvider<Displayable, BaseViewHolder> {

    public abstract boolean isForViewType(@NonNull Displayable item);

    @Override
    public int viewType() {
        return 0;
    }

    @Override
    public void convert(BaseViewHolder helper, Displayable data, int position) {

    }
}
