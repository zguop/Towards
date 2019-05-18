package com.waitou.wt_library.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * author   itxp
 * date     2016/7/2 17:11
 * des      fragment 懒加载
 */
public abstract class LazyFragment extends Fragment {

    /**
     * 控件是否初始化完毕
     */
    protected boolean isViewCreated;

    /**
     * 数据是否已经加载完毕
     */
    protected boolean isLoadCompleted;

    /**
     * 解决ViewPager嵌套时Fragment的setUserVisibleHint属性不同步的问题，需要同步属性的标记
     */
    private boolean waitingShowToUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isViewCreated = true;
        return onCreateView(inflater, container);
    }

    public abstract View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            Fragment parentFragment = getParentFragment();
            if (parentFragment != null && !parentFragment.getUserVisibleHint()) {
                waitingShowToUser = true;
                //手动设置自己为不可见
                super.setUserVisibleHint(false);
            }
        }

        if (getUserVisibleHint() && !isLoadCompleted) {
            visibleCall();
        }
    }

    /**
     * 这个方法会先调用，再调用onActivityCreated
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //解决ViewPager嵌套时Fragment的setUserVisibleHint属性不同步的问题
        if (getActivity() != null) {
            List<Fragment> childFragmentList = getChildFragmentManager().getFragments();
            if (childFragmentList.size() > 0) {
                for (Fragment fragment : childFragmentList) {
                    if (fragment instanceof LazyFragment) {
                        LazyFragment childBaseFragment = (LazyFragment) fragment;
                        if (isVisibleToUser && childBaseFragment.waitingShowToUser) {
                            childBaseFragment.waitingShowToUser = false;
                            fragment.setUserVisibleHint(true);
                        } else {
                            if (childBaseFragment.getUserVisibleHint()) {
                                childBaseFragment.waitingShowToUser = true;
                                fragment.setUserVisibleHint(false);
                            }
                        }
                    }
                }
            }
        }
        //fragment 可见且View初始化完毕 并且数据未加载 调用 visibleCall
        if (getUserVisibleHint() && isViewCreated && !isLoadCompleted) {
            visibleCall();
        }
    }


    /**
     * fragment的懒加载 默认可见调用一次后，不会再次调用
     * isLoadCompleted 改为false，那么每次可见都会调用该方法
     */
    protected void visibleCall() {
        isLoadCompleted = true;
    }
}
