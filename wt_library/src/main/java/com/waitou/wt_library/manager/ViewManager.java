package com.waitou.wt_library.manager;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;

import com.billy.android.loading.Gloading;
import com.waitou.wt_library.R;
import com.waitou.wt_library.base.IView;
import com.waitou.wt_library.view.TowardsToolbar;

import cn.droidlover.xstatecontroller.XStateController;

/**
 * auth aboom
 * date 2019/4/7
 */
public class ViewManager {

    private Context         context;
    private ViewGroup       groupView;
    private Gloading.Holder holder;

    public ViewManager(ViewGroup content) {
        groupView = content;
        context = content.getContext();
    }

    public static ViewManager getManager(Activity activity) {
        CoordinatorLayout content = new CoordinatorLayout(activity);
        return getManager(activity, content);
    }

    public static ViewManager getManager(Activity activity, ViewGroup viewGroup) {
        ViewGroup wrapper = activity.findViewById(android.R.id.content);
        wrapper.addView(viewGroup, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return getManager(viewGroup);
    }

    public static ViewManager getManager(ViewGroup viewGroup) {
        return new ViewManager(viewGroup);
    }

    public void wrapXStateController(IView iView, boolean isWrap) {
        View contentView = iView.getContentView();
        if (contentView == null) {
            throw new IllegalStateException("contentView can not be null");
        }
        if (iView.getContentView().getParent() != null) {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            parent.removeView(contentView);
        }
        if (isWrap) {
            XStateController xStateController = (XStateController) View.inflate(context, R.layout.base_view_state, null);
            xStateController.contentView(contentView);
            this.groupView.addView(xStateController, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            setLayoutParamsCoordinator(xStateController);
            holder = Gloading.getDefault().wrap(xStateController).withRetry(iView.run());
        } else {
            groupView.addView(contentView);
            setLayoutParamsCoordinator(contentView);
        }
    }

    public TowardsToolbar wrapBar() {
        AppBarLayout appBarLayout = new AppBarLayout(context);
        TowardsToolbar toolbar = new TowardsToolbar(context);
        appBarLayout.addView(toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        groupView.addView(appBarLayout, 0);
        return toolbar;
    }

    public void showLoading() {
        holder.showLoading();
    }

    public void showContent() {
        holder.showLoadSuccess();
    }

    public void showFailed() {
        holder.showLoadFailed();
    }

    public void showEmpty() {
        holder.showEmpty();
    }

    private void setLayoutParamsCoordinator(View view) {
        if (groupView instanceof CoordinatorLayout) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
            layoutParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());
            view.setLayoutParams(layoutParams);
        }
    }
}
