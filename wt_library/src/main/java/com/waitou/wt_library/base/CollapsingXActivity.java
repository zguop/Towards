package com.waitou.wt_library.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.waitou.wt_library.R;
import com.waitou.wt_library.databinding.ActivityXCollapsingToolbarBinding;


/**
 * Created by waitou on 17/2/10.
 */

public abstract class CollapsingXActivity<P extends UIPresent, D extends ViewDataBinding> extends BaseActivity implements UIView<P> {

    private VDelegate                         mVDelegate;
    private ActivityXCollapsingToolbarBinding mXBinding;

    private P presenter;
    private D d;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initXView()) {
            mXBinding = DataBindingUtil.setContentView(this, R.layout.activity_x_collapsing_toolbar);
            initReloadData(mXBinding.xContentLayout.getErrorView().findViewById(R.id.error));
            if (getContentViewId() > 0) {
                d = DataBindingUtil.inflate(getLayoutInflater(), getContentViewId(), null, false);
                mXBinding.xContentLayout.addContentView(d.getRoot());
            }
        } else {
            if (getContentViewId() > 0) {
                d = DataBindingUtil.setContentView(this, getContentViewId());
            }
        }

        if (getP() == null) {
            setPresenter(createPresenter());
            if (getP() != null) {
                getP().attachV(this);
            }
        }

        initData(savedInstanceState);
    }

    public ActivityXCollapsingToolbarBinding getXBinding() {
        return mXBinding;
    }

    protected D getBinding() {
        return d;
    }

    protected P getP() {
        return presenter;
    }

    protected VDelegate getUiDelegate() {
        if (mVDelegate == null) {
            mVDelegate = VDelegateBase.create(this);
        }
        return mVDelegate;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getUiDelegate().resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getUiDelegate().pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getP() != null) {
            getP().detachV();
            presenter = null;
        }
        getUiDelegate().destroy();
        mVDelegate = null;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    protected abstract P createPresenter();

    protected void initReloadData(View view) {
        view.findViewById(R.id.error).setOnClickListener(v -> reloadData());
    }

    /*--------------- toolbar的初始化 initXView 返回true 使用默认布局---------------*/
    protected void initMenuActionBar(String title) {
        mXBinding.toolbar.initMenuActionBar(title);
    }

    protected void initMenuActionBar(String title, String menuText, View.OnClickListener listener) {
        mXBinding.toolbar.initMenuActionBar(title, menuText, listener);
    }

    protected void initIconActionBar(String title, int menuIcon, View.OnClickListener listener) {
        mXBinding.toolbar.initIconActionBar(title, menuIcon, listener);
    }

    protected void setBackListener(@IdRes int resId, View.OnClickListener listener) {
        mXBinding.toolbar.setBackListener(resId, listener);
    }

    protected void setTitle(String title) {
        mXBinding.toolbar.setTitle(title);
    }

    protected void fromCustomMenuView(ViewDataBinding dataBinding, int bindingKey) {
        mXBinding.toolbar.fromCustomMenuView(dataBinding, bindingKey);
    }

    /*--------------- 界面状态 ---------------*/
    public void showContent() {
        mXBinding.xContentLayout.showContent();
    }

    public void showEmpty() {
        mXBinding.xContentLayout.showEmpty();
    }

    public void showError() {
        mXBinding.xContentLayout.showError();
    }

    public void showLoading() {
        mXBinding.xContentLayout.showLoading();
    }
}
