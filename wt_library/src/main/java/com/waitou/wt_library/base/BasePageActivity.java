package com.waitou.wt_library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.waitou.wt_library.manager.ViewManager;

/**
 * auth aboom
 * date 2019/4/6
 */
public abstract class BasePageActivity extends BaseActivity implements IView {

    public ViewManager viewManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initManager();
        initLoadingStatusViewIfNeed();
        afterCreate(savedInstanceState);
    }

    public abstract void reloadData();

    public abstract void afterCreate(Bundle savedInstanceState);

    protected void initManager() {
        viewManager = ViewManager.getManager(this);
    }

    protected void initLoadingStatusViewIfNeed() {
        viewManager.wrapXStateController(this, true);
    }

    @Override
    public Runnable run() {
        return this::reloadData;
    }

    public void showLoading() {
        viewManager.showLoading();
    }

    public void showContent() {
        viewManager.showContent();
    }

    public void showFailed() {
        viewManager.showFailed();
    }

    public void showEmpty() {
        viewManager.showEmpty();
    }
}
