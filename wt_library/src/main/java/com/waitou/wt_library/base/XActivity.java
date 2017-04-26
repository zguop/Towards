package com.waitou.wt_library.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.waitou.net_library.BuildConfig;
import com.waitou.wt_library.R;
import com.waitou.wt_library.databinding.ActivityXBinding;
import com.waitou.wt_library.recycler.XPullRecyclerView;


/**
 * Created by wanglei on 2016/11/27.
 */

public abstract class XActivity<P extends UIPresent, D extends ViewDataBinding> extends BaseActivity implements UIView<P> {

    private ActivityXBinding mXBinding;
    private RxPermissions    mRxPermissions;
    private P                presenter;
    private D                d;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (defaultXView()) {
            mXBinding = DataBindingUtil.setContentView(this, R.layout.activity_x);
            initReloadData(mXBinding.xContentLayout.getErrorView().findViewById(R.id.error));
            if (getContentViewId() > 0) {
                d = DataBindingUtil.inflate(getLayoutInflater(), getContentViewId(), null, false);
                mXBinding.xContentLayout.addContentView(d.getRoot());
            }
            if (defaultLoading()) {
                showLoading();
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

    public ActivityXBinding getXBinding() {
        return mXBinding;
    }

    protected D getBinding() {
        return d;
    }

    public P getP() {
        return presenter;
    }

    public RxPermissions getRxPermissions() {
        if (mRxPermissions == null) {
            mRxPermissions = new RxPermissions(this);
            mRxPermissions.setLogging(BuildConfig.DEBUG);
        }
        return mRxPermissions;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getP() != null) {
            getP().detachV();
            presenter = null;
        }
    }

    @Override
    public P createPresenter() {
        return null;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean defaultXView() {
        return true;
    }

    @Override
    public boolean defaultLoading() {
        return false;
    }

    protected void initReloadData(View view) {
        view.findViewById(R.id.error).setOnClickListener(v -> reloadData());
    }

    /*--------------- toolbar的初始化 defaultXView 返回true 使用默认布局---------------*/
    protected void initMenuActionBar(CharSequence title) {
        mXBinding.toolbar.initMenuActionBar(title);
    }

    protected void initMenuActionBar(CharSequence title, CharSequence menuText, View.OnClickListener listener) {
        mXBinding.toolbar.initMenuActionBar(title, menuText, listener);
    }

    protected void initIconActionBar(CharSequence title, int menuIcon, View.OnClickListener listener) {
        mXBinding.toolbar.initIconActionBar(title, menuIcon, listener);
    }

    protected void setBackListener(@DrawableRes int resId, View.OnClickListener listener) {
        mXBinding.toolbar.setBackListener(resId, listener);
    }

    protected void setToolbarTitle(CharSequence title) {
        mXBinding.toolbar.setTitle(title);
    }

    protected void setToolbarRightText(CharSequence rightText) {
        mXBinding.toolbar.setRightText(rightText);
    }

    protected void fromCustomMenuView(ViewDataBinding dataBinding, int bindingKey) {
        mXBinding.toolbar.fromCustomMenuView(dataBinding, bindingKey);
    }

    protected void goneToolBar() {
        getUiDelegate().gone(mXBinding.toolbar);
    }

    /*--------------- 界面状态 ---------------*/
    public void showContent() {
        mXBinding.xContentLayout.showContent();
    }

    public void showEmpty() {
        mXBinding.xContentLayout.showEmpty();
    }

    protected void showError() {
        showError(true);
    }

    protected void showError(boolean isReload) {
        if (isReload) {
            mXBinding.xContentLayout.showError();
        } else {
            if (mXBinding.xContentLayout.getContentView() instanceof XPullRecyclerView) {
                ((XPullRecyclerView) mXBinding.xContentLayout.getContentView()).showError(false);
            }
        }
    }

    public void showLoading() {
        mXBinding.xContentLayout.showLoading();
    }
}
