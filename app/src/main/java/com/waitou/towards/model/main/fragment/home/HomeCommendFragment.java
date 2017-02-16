package com.waitou.towards.model.main.fragment.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.waitou.net_library.model.Displayable;
import com.waitou.towards.R;
import com.waitou.towards.bean.BannerAdapterInfo;
import com.waitou.towards.bean.BannerPageInfo;
import com.waitou.towards.databinding.IncludeRecyclerViewBinding;
import com.waitou.towards.model.presenter.MainPresenter;
import com.waitou.wt_library.base.XFragment;
import com.waitou.wt_library.recycler.adapter.MultiTypeAdapter;
import com.waitou.wt_library.view.viewpager.SingleViewPagerAdapter;

import java.util.List;


/**
 * Created by waitou on 17/2/10.
 */

public class HomeCommendFragment extends XFragment<MainPresenter, IncludeRecyclerViewBinding> {

    private MainPresenter                                                    mPresenter;
    private MultiTypeAdapter<Displayable>                                    mAdapter;
    private SingleViewPagerAdapter<BannerPageInfo.BannerInfo.BannerDataInfo> mBannerAdapter;

    @Override
    public boolean initXView() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.include_recycler_view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mAdapter = new MultiTypeAdapter<>(getActivity());
        mAdapter.addViewTypeToLayoutMap(0, R.layout.item_banner);
        mAdapter.addViewTypeToLayoutMap(1, R.layout.fragment_home);
        getBinding().xList.verticalLayoutManager(getActivity()).setAdapter(mAdapter);
        reloadData();
    }

    @Override
    public void reloadData() {
        mPresenter.loadHomeData();
    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        this.mPresenter = presenter;
    }

    public static Fragment getInstance(MainPresenter presenter) {
        HomeCommendFragment fragment = new HomeCommendFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }

    public void bannerSuccess(List<BannerPageInfo.BannerInfo.BannerDataInfo> bannerPageInfo) {
        if (mBannerAdapter == null) {
            mBannerAdapter = new SingleViewPagerAdapter<>(getActivity(), bannerPageInfo, R.layout.item_banner_image);
            mBannerAdapter.setPresenter(mPresenter);
            mAdapter.add(0, new BannerAdapterInfo(mBannerAdapter), 0);
        } else {
            mBannerAdapter.set(bannerPageInfo);
        }
    }
}
