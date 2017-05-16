package com.waitou.towards.model.activity;

import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.waitou.wt_library.recycler.adapter.BindingViewHolder;

/**
 * Created by waitou on 17/5/10.
 */

public class SubAdapter extends DelegateAdapter.Adapter<BindingViewHolder> {

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return null;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
