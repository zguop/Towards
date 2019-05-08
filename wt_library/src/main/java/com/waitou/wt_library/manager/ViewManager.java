package com.waitou.wt_library.manager;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.billy.android.loading.Gloading;
import com.waitou.wt_library.R;
import com.waitou.wt_library.base.IView;
import com.waitou.wt_library.view.TitleBar;

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
        context = content.getContext();
        groupView = content;
    }

    public static ViewManager getManager(AppCompatActivity activity) {
        CoordinatorLayout content = new CoordinatorLayout(activity);
        return getManager(activity, content);
    }

    public static ViewManager getManager(AppCompatActivity activity, ViewGroup viewGroup) {
        activity.setContentView(viewGroup, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
        if (contentView.getParent() != null) {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            parent.removeView(contentView);
        }
        if (isWrap) {
            XStateController xStateController = (XStateController) View.inflate(context, R.layout.base_view_state, null);
            xStateController.setId(R.id.page_content);
            xStateController.contentView(contentView);
            this.groupView.addView(xStateController, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            holder = Gloading.getDefault().wrap(xStateController).withRetry(iView.run());
        } else {
            contentView.setId(R.id.page_content);
            groupView.addView(contentView);
        }
        if (groupView instanceof CoordinatorLayout) {
            View pageContentView = groupView.findViewById(R.id.page_content);
            while (!(pageContentView.getParent() instanceof CoordinatorLayout)) {
                pageContentView = (View) pageContentView.getParent();
            }
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) pageContentView.getLayoutParams();
            layoutParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());
            pageContentView.setLayoutParams(layoutParams);
        }
    }

    public TitleBar wrapBar() {
        AppBarLayout appBarLayout = new AppBarLayout(context);
        appBarLayout.setId(R.id.page_title_bar);
        TitleBar toolbar = new TitleBar(context);
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
}
