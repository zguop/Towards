package com.waitou.wt_library.manager;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.waitou.wt_library.R;

/**
 * auth aboom
 * date 2019-05-19
 */
public class RootViewManager {

    public static ViewGroup attachViewGet(Activity context) {
        return attachViewGet(context, new CoordinatorLayout(context));
    }

    public static ViewGroup attachViewGet(Activity context, ViewGroup rootView) {
        if (rootView instanceof LinearLayout) {
            ((LinearLayout) rootView).setOrientation(LinearLayout.VERTICAL);
        }
        rootView.setId(R.id.pageRootView);
        //默认会走向activity的子类添加打view中
        context.setContentView(rootView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return rootView;
    }
}
