package cn.droidlover.xdroid.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.droidlover.xdroid.R;
import cn.droidlover.xdroid.databinding.FragmentXBinding;
import cn.droidlover.xdroid.recycler.XPullRecyclerView;

/**
 * Created by wanglei on 2016/11/27.
 */

public abstract class XFragment<P extends UIPresent, D extends ViewDataBinding> extends BaseFragment implements UIView<P> {

    private VDelegate        mVDelegate;
    private FragmentXBinding mXBinding;

    private D d;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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


    /**
     * 设置错误页面的按钮点击事件 注：如果使用的是pullRecyclerView显示错误页面 则需要手动设置调用
     *
     * @param view 错误页面的View
     */
    public void initReloadData(View view) {
        view.findViewById(R.id.error).setOnClickListener(v -> reloadData());
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getVDelegate().destroy();
        mVDelegate = null;
    }


    /*--------------- 界面状态 ---------------*/
    protected void showContent() {
        mXBinding.xContentLayout.showContent();
    }

    protected void showEmpty() {
        mXBinding.xContentLayout.showEmpty();
    }

    protected void showError(boolean isReload) {
        if(isReload){
            mXBinding.xContentLayout.showError();
        }else {
            if (mXBinding.xContentLayout.getContentView() instanceof XPullRecyclerView) {
                ((XPullRecyclerView) mXBinding.xContentLayout.getContentView()).showError(false);
            }
        }
    }

    protected void showLoading() {
        mXBinding.xContentLayout.showLoading();
    }
}
