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

//
//    public void wrapXStateController(IView iView, boolean isWrap) {
//        View contentView = iView.getContentView();
//        if (contentView == null) {
//            throw new IllegalStateException("contentView can not be null");
//        }
//        if (contentView.getParent() != null) {
//            ViewGroup parent = (ViewGroup) contentView.getParent();
//            parent.removeView(contentView);
//        }
//        if (isWrap) {
//            XStateController xStateController = (XStateController) View.inflate(context, R.layout.base_view_state, null);
//            xStateController.setId(R.id.page_content);
//            xStateController.contentView(contentView);
//            this.rootView.addView(xStateController, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            holder = Gloading.getDefault().wrap(xStateController).withRetry(iView.run());
//        } else {
//            contentView.setId(R.id.page_content);
//            rootView.addView(contentView);
//        }
//        if (rootView instanceof CoordinatorLayout) {
//            View pageContentView = rootView.findViewById(R.id.page_content);
//            while (!(pageContentView.getParent() instanceof CoordinatorLayout)) {
//                pageContentView = (View) pageContentView.getParent();
//            }
//            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) pageContentView.getLayoutParams();
//            layoutParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());
//            pageContentView.setLayoutParams(layoutParams);
//        }
//    }
//
//
//    public TitleBar wrapBar() {
//        TitleBar toolbar = new TitleBar(context);
//        toolbar.setId(R.id.page_title_bar);
//        rootView.addView(toolbar, 0);
//        return toolbar;
//    }
//
//    public void showLoading() {
//        holder.showLoading();
//    }
//
//    public void showContent() {
//        holder.showLoadSuccess();
//    }
//
//    public void showFailed() {
//        holder.showLoadFailed();
//    }
//
//    public void showEmpty() {
//        holder.showEmpty();
//    }
}
