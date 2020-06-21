package com.to.aboomy.recycler_lib.delegate;

import android.content.Context;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.to.aboomy.recycler_lib.adapter.Displayable;

/**
 * auth aboom
 * date 2019-05-06
 */
public abstract class AdapterDelegate {

    public Context context;

    public abstract boolean isForViewType(@NonNull Displayable displayable);

    public abstract int layout();

    protected void viewHolderCreated(@NonNull BaseViewHolder helper) {}

    public abstract void convert(BaseViewHolder helper, Displayable data, int position);

}
