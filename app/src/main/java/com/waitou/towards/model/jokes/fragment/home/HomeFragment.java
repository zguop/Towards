package com.waitou.towards.model.jokes.fragment.home;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.waitou.towards.BR;
import com.waitou.towards.R;
import com.waitou.towards.databinding.IncludeViewPagerBinding;
import com.waitou.towards.databinding.ViewSmartLayoutBinding;
import com.waitou.towards.model.jokes.contract.MainContract;
import com.waitou.towards.model.presenter.MainPresenter;
import com.waitou.wt_library.base.XFragment;
import com.waitou.wt_library.base.XFragmentAdapter;

import java.util.List;



/**
 * Created by waitou on 16/12/23.
 * 首页
 */

public class HomeFragment extends XFragment<MainPresenter, IncludeViewPagerBinding> implements MainContract.HomeView, SmartTabLayout.TabProvider {

    private MainPresenter           mPresenter;
    private ViewSmartLayoutBinding  mLayoutBinding;

    @Override
    public boolean initXView() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return R.layout.include_view_pager;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initToolBar(getActivity());
        List<Fragment> homeFragmentList = mPresenter.getHomeFragmentList();
        XFragmentAdapter adapter = new XFragmentAdapter(getChildFragmentManager(), homeFragmentList, null);
        getBinding().setAdapter(adapter);
        mLayoutBinding.setViewPager(getBinding().viewPager);
    }

    @Override
    public void reloadData() {
    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        this.mPresenter = presenter;
    }

    public ViewDataBinding initToolBar(Activity activity) {
        if (mLayoutBinding == null) {
            mLayoutBinding = DataBindingUtil.inflate(activity.getLayoutInflater(), R.layout.view_smart_layout, null, false);
            mLayoutBinding.setProvider(this);
        }
        return mLayoutBinding;
    }

    public ViewDataBinding getHomeToolbar(Activity activity) {
        return initToolBar(activity);
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        ViewDataBinding viewDataBinding = bindingInflate(R.layout.toolbar_home_title, container);
        viewDataBinding.setVariable(BR.position,position);
        return viewDataBinding.getRoot();
    }
}
