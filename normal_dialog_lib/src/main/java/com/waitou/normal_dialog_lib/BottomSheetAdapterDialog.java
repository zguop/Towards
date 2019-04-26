package com.waitou.normal_dialog_lib;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * auth aboom
 * date 2019-04-26
 */
public class BottomSheetAdapterDialog extends NormalDialog implements IDialogView {

    private RecyclerView.Adapter adapter;
    private String               title;
    private int                  itemHeight;
    private int                  spanCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogView(this);
        setGravity(Gravity.BOTTOM);
        setWidget(WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    public View getContentView(Context activity) {
        View inflate = View.inflate(activity, R.layout.dl_bottom_adapter_list, null);
        TextView dlTitle = inflate.findViewById(R.id.dl_title);
        RecyclerView dlList = inflate.findViewById(R.id.dl_list);
        dlTitle.setText(title);
        dlTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        dlList.setLayoutManager(spanCount > 0 ? new GridLayoutManager(activity, spanCount) : new LinearLayoutManager(activity));
        dlList.setAdapter(adapter);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        int itemCount = spanCount > 0 ?
                ((GridLayoutManager) ((RecyclerView) view.findViewById(R.id.dl_list))
                        .getLayoutManager()).getSpanCount() : adapter.getItemCount();
        int countHeight = itemCount * itemHeight;
        if (height > 0 && countHeight < height) {
            setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    public BottomSheetAdapterDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public BottomSheetAdapterDialog setItemHeight(int height) {
        this.itemHeight = height;
        return this;
    }

    public BottomSheetAdapterDialog setRecyclerAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public BottomSheetAdapterDialog grid(int spanCount) {
        this.spanCount = spanCount;
        return this;
    }
}
