package com.waitou.wt_library.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

    private VDelegate         mVDelegate;
    private FragmentXBinding  mXBinding;
    private LayoutInflater    mInflater;
    private AppCompatActivity mAppCompatActivity;

    private D d;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = inflater;
        if (initXView()) {
            mXBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_x, container, false);
            initReloadData(mXBinding.xContentLayout.getErrorView());
            if (getContentViewId() > 0) {
                d = DataBindingUtil.inflate(inflater, getContentViewId(), null, false);
                mXBinding.xContentLayout.addContentView(d.getRoot());
            }
        } else {
            if (getContentViewId() > 0) {
                d = DataBindingUtil.inflate(inflater, getContentViewId(), null, false);
            }
        }

        return initXView() ? mXBinding.getRoot() : d.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
        isViewCreated = true;
        if (getUserVisibleHint() && fragmentVisibleHint()) {
            isLoadDataCompleted = true;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            this.mAppCompatActivity = (AppCompatActivity) context;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getVDelegate().destroy();
        mVDelegate = null;
    }

    protected void initReloadData(View view) {
        view.findViewById(R.id.error).setOnClickListener(v -> reloadData());
    }

    protected VDelegate getVDelegate() {
        if (mVDelegate == null) {
            mVDelegate = VDelegateBase.create(getContext());
        }
        return mVDelegate;
    }

    protected D getBinding() {
        return d;
    }

    protected FragmentXBinding getXBinding() {
        return mXBinding;
    }

    protected AppCompatActivity getAppCompatActivity() {
        return mAppCompatActivity;
    }

    protected LayoutInflater getInflater() {
        if (mInflater == null) {
            return getActivity().getLayoutInflater();
        }
        return mInflater;
    }


    /*--------------- 界面状态 ---------------*/
    protected void showContent() {
        mXBinding.xContentLayout.showContent();
    }

    protected void showEmpty() {
        mXBinding.xContentLayout.showEmpty();
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

    protected void showLoading() {
        mXBinding.xContentLayout.showLoading();
    }
}
