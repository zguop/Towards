package com.waitou.wt_library.manager;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.view.ViewGroup;

import com.waitou.wt_library.R;

/**
 * auth aboom
 * date 2019-05-19
 */
public class RootViewManager {

    public static ViewGroup attachViewGet(Activity context) {
        CoordinatorLayout toolbar = new CoordinatorLayout(context);
        toolbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        toolbar.setId(R.id.page_root_view);
        context.setContentView(toolbar);
        return toolbar;
    }
}
