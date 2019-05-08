package com.to.aboomy.recycler_lib;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * auth aboom
 * date 2019-05-06
 */
public abstract class AdapterDelegate {

    public Context context;

    public abstract boolean isForViewType(@NonNull Displayable displayable);

    public abstract int layout();

    public void convert(BaseViewHolder helper, Displayable data, int position) {}
}
