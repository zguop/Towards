package com.waitou.wt_library.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * author   itxp
 * date     2016/7/2 17:11
 * des      fragment 基类 ,可扩展
 */
public abstract class BaseFragment extends Fragment {

    private CompositeDisposable mCompositeDisposable;

    /**
     * 控件是否初始化完毕
     */
    protected boolean isViewCreated;

    /**
     * 数据是否已经加载完毕
     */
    protected boolean isLoadDataCompleted;

    /**
     * fragment是否要被恢复可见的标识
     */
    private boolean waitingShowToUser;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //当前fragment可见
        if (getUserVisibleHint()) {
            //获取父fragment
            Fragment parentFragment = getParentFragment();
            //如果父fragment不为空且不可见的
            if (parentFragment != null && !parentFragment.getUserVisibleHint()) {
                //设置它的可见属性
                waitingShowToUser = true;
                super.setUserVisibleHint(false);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //解决ViewPager嵌套时Fragment的setUserVisibleHint属性不同步的问题
        if (getActivity() != null) {
            //获取当前fragment的子fragment
            List<Fragment> childFragmentList = getChildFragmentManager().getFragments();
            if (childFragmentList != null && childFragmentList.size() > 0) {
                for (Fragment fragment : childFragmentList) {
                    if (fragment instanceof BaseFragment) {
                        BaseFragment childBaseFragment = (BaseFragment) fragment;
                        //如果当前的fragment是可见的 并且子fragment
                        if (getUserVisibleHint() && childBaseFragment.waitingShowToUser) {
                            fragment.setUserVisibleHint(true);
                            childBaseFragment.waitingShowToUser = false;
                        } else {
                            if (childBaseFragment.getUserVisibleHint()) {
                                fragment.setUserVisibleHint(false);
                                childBaseFragment.waitingShowToUser = true;
                            }
                        }
                    }
                }
            }
        }

        //fragment 可见且View初始化完毕 并且数据未加载 调用 fragmentVisibleHint
        if (getUserVisibleHint() && isViewCreated && !isLoadDataCompleted) {
            fragmentVisibleHint();
            isLoadDataCompleted = true;

        }
    }


    /**
     * fragment的懒加载
     */
    protected void fragmentVisibleHint() {
    }

    /**
     * 向队列中添加一个 subscription
     */
    public void pend(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        if (disposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    protected <D extends ViewDataBinding> D bindingInflate(@LayoutRes int resId, ViewGroup container) {
        return DataBindingUtil.inflate(getLayoutInflater(), resId, container, container != null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
