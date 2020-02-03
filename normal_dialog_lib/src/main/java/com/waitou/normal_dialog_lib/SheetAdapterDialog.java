package com.waitou.normal_dialog_lib;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * auth aboom
 * date 2019-04-26
 */
public class SheetAdapterDialog extends NormalDialog {

    private RecyclerView.Adapter adapter;
    private String title;
    private int itemHeight;
    private int spanCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gravity = Gravity.BOTTOM;
        width = WindowManager.LayoutParams.MATCH_PARENT;
        int itemCount = adapter.getItemCount();
        if (spanCount > 0) {
            itemCount = (int) Math.ceil(itemCount * 1.0f / spanCount);
        }
        int maxHeight = getResources().getDimensionPixelSize(R.dimen.dl_bottom_sheet_dialog_default_height);
        int countHeight = itemCount * itemHeight;
        height = countHeight < maxHeight ? WindowManager.LayoutParams.WRAP_CONTENT : maxHeight;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dl_bottom_adapter_list, container, false);
        TextView dlTitle = inflate.findViewById(R.id.dl_title);
        RecyclerView dlList = inflate.findViewById(R.id.dl_list);
        dlTitle.setText(title);
        dlTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        dlList.setLayoutManager(spanCount > 0 ? new GridLayoutManager(getActivity(), spanCount) : new LinearLayoutManager(getActivity()));
        dlList.setAdapter(adapter);
        return inflate;
    }

    public SheetAdapterDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SheetAdapterDialog setItemHeight(int height) {
        this.itemHeight = height;
        return this;
    }

    public SheetAdapterDialog setRecyclerAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public SheetAdapterDialog grid(int spanCount) {
        this.spanCount = spanCount;
        return this;
    }
}
