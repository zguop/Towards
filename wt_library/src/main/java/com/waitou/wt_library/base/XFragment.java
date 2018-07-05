package com.waitou.wt_library.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waitou.wt_library.R;
import com.waitou.wt_library.databinding.FragmentXBinding;
import com.waitou.wt_library.recycler.XPullRecyclerView;


/**
 * Created by wanglei on 2016/11/27.
 */

public abstract class XFragment<P extends UIPresent, D extends ViewDataBinding> extends BaseFragment implements UIView<P> {

    private FragmentXBinding mXBinding;
    public  P presenter;
    private D d;

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mXBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_x, container, false);
        initReloadData(mXBinding.xContentLayout.getErrorView());
        if (getContentViewId() > 0) {
            d = DataBindingUtil.inflate(inflater, getContentViewId(), null, false);
            mXBinding.xContentLayout.addContentView(d.getRoot());
        }
        isViewCreated = true;
        if (presenter == null) {
            presenter = createPresenter();
            if (presenter != null) {
                presenter.attachV(this);
            }
        }
        return mXBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        afterCreate(savedInstanceState);
        //fragment可见 且 数据未加载 调用 fragmentVisibleHint
        if (getUserVisibleHint() && !isLoadDataCompleted) {
            fragmentVisibleHint();
            isLoadDataCompleted = true;
        }
    }

    public P getP() { return presenter; }

    @Override
    public P createPresenter() {
        return null;
    }

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    protected void initReloadData(View view) {
        view.findViewById(R.id.error).setOnClickListener(v -> reloadData());
    }

    protected D getBinding() {
        return d;
    }

    protected FragmentXBinding getXBinding() {
        return mXBinding;
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
