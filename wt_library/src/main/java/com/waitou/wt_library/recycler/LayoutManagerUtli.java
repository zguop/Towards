package com.waitou.wt_library.recycler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by waitou on 17/2/20.
 */

public class LayoutManagerUtli {

    public static LinearLayoutManager getVerticalLayoutManager(Context context) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    public static LinearLayoutManager getHorizontalLayoutManager(Context context) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return manager;
    }

    public static GridLayoutManager getGridLayoutManager(Context context, int spanCount) {
        GridLayoutManager manager = new GridLayoutManager(context, spanCount);
//        manager.setInitialPrefetchItemCount();
        return manager;
    }

    public static StaggeredGridLayoutManager getVerticalStaggeredLayoutManager(int spanCount) {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        return manager;
    }

    public StaggeredGridLayoutManager getHorizontalStaggeredLayoutManager(int spanCount) {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
        return manager;
    }
}
