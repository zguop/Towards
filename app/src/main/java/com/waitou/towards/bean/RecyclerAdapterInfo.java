package com.waitou.towards.bean;

import android.support.v7.widget.RecyclerView;

import com.waitou.net_library.model.Displayable;

/**
 * Created by waitou on 17/2/20.
 */

public class RecyclerAdapterInfo implements Displayable {

    public RecyclerView.Adapter       adapter;
    public RecyclerView.LayoutManager layoutManager;

    public RecyclerAdapterInfo(RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager) {
        this.adapter = adapter;
        this.layoutManager = layoutManager;
    }
}
