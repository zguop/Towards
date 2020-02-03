package com.waitou.wt_library.manager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billy.android.loading.Gloading;
import com.waitou.widget_lib.StateController;
import com.waitou.wt_library.R;
import com.waitou.wt_library.base.IView;

/**
 * auth aboom
 * date 2019/4/7
 */
public class StateViewManager {

    public static Gloading.Holder wrapStateController(IView iView, boolean attachToRoot) {
        View contentView = iView.getContentView();
        if (contentView == null) {
            throw new IllegalStateException("contentView can not be null");
        }
        if (contentView.getParent() != null) {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            parent.removeView(contentView);
        }
        if (contentView.getId() == View.NO_ID) {
            contentView.setId(R.id.pageContentView);
        }
        Context context = contentView.getContext();
        ViewGroup rootView = null;
        if (attachToRoot) {
            if (context instanceof Activity) {
                rootView = ((Activity) context).findViewById(R.id.pageRootView);
            }
        }
        StateController stateController = (StateController) LayoutInflater.from(context).inflate(R.layout.base_view_state, rootView, false);
        stateController.setId(R.id.pageStateView);
        stateController.contentView(contentView);
        if (rootView != null) {
            rootView.addView(stateController);
        }
        return Gloading.getDefault().wrap(stateController).withRetry(iView);
    }
}
