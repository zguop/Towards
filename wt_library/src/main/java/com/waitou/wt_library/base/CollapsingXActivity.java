package com.waitou.wt_library.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.View;

import com.waitou.wt_library.R;
import com.waitou.wt_library.databinding.ActivityXCollapsingToolbarBinding;


/**
 * Created by waitou on 17/2/10.
 */

public abstract class CollapsingXActivity<P extends UIPresent, D extends ViewDataBinding> extends XActivity<P, ActivityXCollapsingToolbarBinding> {

    private D d;

    @Override
    public boolean initXView() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_x_collapsing_toolbar;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initReloadData(getBinding().xContentLayout.getErrorView().findViewById(R.id.error));
        if (getCollContentViewId() > 0) {
            d = DataBindingUtil.inflate(getLayoutInflater(), getCollContentViewId(), null, false);
            getBinding().xContentLayout.addContentView(d.getRoot());
        }
        initCollData(savedInstanceState);
    }

    public D getCollBinding() {
        return d;
    }

    protected abstract int getCollContentViewId();

    protected abstract void initCollData(Bundle savedInstanceState);


    /*--------------- toolbar的初始化 initXView 返回true 使用默认布局---------------*/
    @Override
    protected void initMenuActionBar(String title) {
        getBinding().toolbar.initMenuActionBar(title);
    }

    @Override
    protected void initMenuActionBar(String title, String menuText, View.OnClickListener listener) {
        getBinding().toolbar.initMenuActionBar(title, menuText, listener);
    }

    @Override
    protected void initIconActionBar(String title, int menuIcon, View.OnClickListener listener) {
        getBinding().toolbar.initIconActionBar(title, menuIcon, listener);
    }

    @Override
    protected void setBackListener(@DrawableRes int resId, View.OnClickListener listener) {
        getBinding().toolbar.setBackListener(resId, listener);
    }

    @Override
    protected void setTitle(String title) {
        getBinding().toolbar.setTitle(title);
    }

    @Override
    protected void fromCustomMenuView(ViewDataBinding dataBinding, int bindingKey) {
        getBinding().toolbar.fromCustomMenuView(dataBinding, bindingKey);
    }

    @Override
    protected void goneToolBar() {
        getUiDelegate().gone(getBinding().appbar);
        getUiDelegate().gone(getBinding().toolbar);
    }

    /*--------------- 界面状态 ---------------*/
    @Override
    public void showContent() {
        getBinding().xContentLayout.showContent();
    }

    @Override
    public void showEmpty() {
        getBinding().xContentLayout.showEmpty();
    }

    @Override
    public void showError() {
        getBinding().xContentLayout.showError();
    }

    @Override
    public void showLoading() {
        getBinding().xContentLayout.showLoading();
    }
}
