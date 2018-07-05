package com.waitou.wt_library.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.waitou.net_library.BuildConfig;
import com.waitou.wt_library.R;
import com.waitou.wt_library.databinding.ActivityXBinding;
import com.waitou.wt_library.recycler.XPullRecyclerView;
import com.waitou.wt_library.view.TowardsToolbar;


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
        mXBinding = DataBindingUtil.setContentView(this, R.layout.activity_x);
        initReloadData(mXBinding.xContentLayout.getErrorView().findViewById(R.id.error));
        if (getContentViewId() > 0) {
            d = DataBindingUtil.inflate(getLayoutInflater(), getContentViewId(), null, false);
            mXBinding.xContentLayout.addContentView(d.getRoot());
        }
        if (presenter == null) {
            presenter = createPresenter();
            if (presenter != null) {
                presenter.attachV(this);
            }
        }
        afterCreate(savedInstanceState);
    }

    public ActivityXBinding getXBinding() {
        return mXBinding;
    }

    public TowardsToolbar getBar(){
        return mXBinding.toolbar;
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

    protected void initReloadData(View view) {
        view.findViewById(R.id.error).setOnClickListener(v -> reloadData());
    }

    /*--------------- 界面状态 ---------------*/
    public void showContent() {
        mXBinding.xContentLayout.showContent();
    }

    public void showEmpty() {
        mXBinding.xContentLayout.showEmpty();
    }

    public void showError() {
        showError(true);
    }

    public void showError(boolean isReload) {
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
