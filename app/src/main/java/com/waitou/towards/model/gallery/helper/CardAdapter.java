package com.waitou.towards.model.gallery.helper;

import android.content.Context;
import android.view.ViewGroup;

import com.waitou.wt_library.recycler.adapter.BindingViewHolder;
import com.waitou.wt_library.recycler.adapter.SingleTypeAdapter;

/**
 * Created by waitou on 17/2/24.
 */

public class CardAdapter<T> extends SingleTypeAdapter<T> {

    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();

    public CardAdapter(Context context, int layoutRes) {
        super(context, layoutRes);
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BindingViewHolder bindingViewHolder = super.onCreateViewHolder(parent, viewType);
        mCardAdapterHelper.onCreateViewHolder(parent, bindingViewHolder.getBinding().getRoot());
        return bindingViewHolder;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView,position,getItemCount());
        super.onBindViewHolder(holder, position);
    }
}
