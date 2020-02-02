package com.waitou.wt_library.manager;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.to.aboomy.recycler_lib.PullRecyclerView;
import com.to.aboomy.recycler_lib.adapter.MultipleAdapter;
import com.waitou.wt_library.R;

/**
 * auth aboom
 * date 2019-05-19
 */
public class RecyclerViewManager {

    public static RecyclerView attachViewGet(Context activity) {
        return attachViewGet(activity, new LinearLayoutManager(activity), new MultipleAdapter());
    }

    public static PullRecyclerView attachViewGetRefresh(Context activity) {
        return attachViewGetRefresh(activity, new LinearLayoutManager(activity), new MultipleAdapter());
    }

    public static RecyclerView attachViewGet(Context activity, RecyclerView.LayoutManager layoutManager, RecyclerView.Adapter adapter) {
        if (!(activity instanceof Activity)) {
            throw new IllegalArgumentException("context is not activity");
        }

        RecyclerView recyclerView = new RecyclerView(activity);
        recyclerView.setId(R.id.pageContentView);
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if (layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager);
        }
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }
        return recyclerView;
    }

    public static PullRecyclerView attachViewGetRefresh(Context activity, RecyclerView.LayoutManager layoutManager, RecyclerView.Adapter adapter) {
        if (!(activity instanceof Activity)) {
            throw new IllegalArgumentException("context is not activity");
        }

        PullRecyclerView recyclerView = new PullRecyclerView(activity);
        recyclerView.setId(R.id.pageContentView);
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if (layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager);
        }
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }
        return recyclerView;
    }
}