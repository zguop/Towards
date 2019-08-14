package com.waitou.wt_library.manager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billy.android.loading.Gloading;
import com.waitou.wt_library.R;
import com.waitou.wt_library.base.IView;

import cn.droidlover.xstatecontroller.XStateController;

/**
 * auth aboom
 * date 2019/4/7
 */
public class StateViewManager {

    public static Gloading.Holder wrapXStateController(IView iView, boolean attachToRoot) {
        View contentView = iView.getContentView();
        if (contentView == null) {
            throw new IllegalStateException("contentView can not be null");
        }
        if (contentView.getParent() != null) {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            parent.removeView(contentView);
        }
        if(contentView.getId() == View.NO_ID){
            contentView.setId(R.id.pageContentView);
        }

        Context context = contentView.getContext();
        ViewGroup rootView = null;
        if (attachToRoot) {
            if (context instanceof Activity) {
                rootView = ((Activity) context).findViewById(R.id.pageRootView);
            }
        }
        XStateController xStateController = (XStateController) LayoutInflater.from(context).inflate(R.layout.base_view_state, rootView, false);
        xStateController.setId(R.id.pageStateView);
        xStateController.contentView(contentView);
        if (rootView != null) {
            rootView.addView(xStateController);
        }
        return Gloading.getDefault().wrap(xStateController).withRetry(iView.run());
    }
}
