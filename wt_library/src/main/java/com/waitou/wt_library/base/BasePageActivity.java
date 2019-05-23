package com.waitou.wt_library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.billy.android.loading.Gloading;
import com.waitou.wt_library.manager.RootViewManager;
import com.waitou.wt_library.manager.StateViewManager;

/**
 * auth aboom
 * date 2019/4/6
 */
public abstract class BasePageActivity extends BaseActivity implements IView {

    private Gloading.Holder holder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RootViewManager.attachViewGet(this);
        holder = StateViewManager.wrapXStateController(this, true);
        afterCreate(savedInstanceState);
    }

    public abstract void reloadData();

    public abstract void afterCreate(Bundle savedInstanceState);

    @Override
    public Runnable run() {
        return this::reloadData;
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
