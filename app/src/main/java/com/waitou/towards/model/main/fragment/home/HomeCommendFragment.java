package com.waitou.towards.model.main.fragment.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.waitou.net_library.model.Displayable;
import com.waitou.towards.R;
import com.waitou.towards.bean.BannerAdapterInfo;
import com.waitou.towards.bean.BannerPageInfo;
import com.waitou.towards.bean.CanInfo;
import com.waitou.towards.bean.GankResultsTypeInfo;
import com.waitou.towards.bean.HomeFunctionInfo;
import com.waitou.towards.bean.RecyclerAdapterInfo;
import com.waitou.towards.databinding.IncludeMatchRecyclerViewBinding;
import com.waitou.towards.model.main.MainPresenter;
import com.waitou.towards.util.AlertToast;
import com.waitou.wt_library.base.XFragment;
import com.waitou.wt_library.recycler.LayoutManagerUtli;
import com.waitou.wt_library.recycler.adapter.MultiTypeAdapter;
import com.waitou.wt_library.recycler.adapter.SingleTypeAdapter;
import com.waitou.wt_library.view.viewpager.SingleViewPagerAdapter;

import java.util.List;


/**
 * Created by waitou on 17/2/10.
 * 首页推荐
 */

public class HomeCommendFragment extends XFragment<MainPresenter, IncludeMatchRecyclerViewBinding> {

    private MainPresenter                                    mPresenter;
    private MultiTypeAdapter<Displayable>                    mAdapter; //整体页面适配器
    private SingleViewPagerAdapter<BannerPageInfo>           mBannerAdapter;//轮播图适配器
    private SingleTypeAdapter<HomeFunctionInfo.FunctionInfo> mFunctionInfoAdapter; //功能图标适配器
    private SingleViewPagerAdapter<String>                   mSingleViewPagerAdapter;//item多个图适配器

    @Override
    public boolean initXView() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.include_match_recycler_view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mAdapter = new MultiTypeAdapter<>(getActivity());
        mAdapter.addViewTypeToLayoutMap(0, R.layout.item_banner);
        mAdapter.addViewTypeToLayoutMap(1, R.layout.item_wrap_recycler_view);
        mAdapter.addViewTypeToLayoutMap(2, R.layout.item_gank_page);
        mAdapter.addViewTypeToLayoutMap(3, R.layout.item_bottom);
        getBinding().setManager(LayoutManagerUtli.getVerticalLayoutManager(getActivity()));
        getBinding().setAdapter(mAdapter);
    }

    @Override
    protected boolean fragmentVisibleHint() {
        reloadData();
        return true;
    }

    @Override
    public void reloadData() {
        showLoading();
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

    public void onBannerSuccess(List<BannerPageInfo> bannerPageInfo) {
        if (mBannerAdapter == null) {
            mBannerAdapter = new SingleViewPagerAdapter<>(getActivity(), bannerPageInfo, R.layout.item_banner_image);
            mBannerAdapter.setPresenter(mPresenter);
            mAdapter.add(0, new BannerAdapterInfo(mBannerAdapter), 0);
        } else {
            mBannerAdapter.set(bannerPageInfo);
        }
    }

    public void onFunctionSuccess(List<HomeFunctionInfo.FunctionInfo> homeFunctionInfo) {
        if (mFunctionInfoAdapter == null) {
            mFunctionInfoAdapter = new SingleTypeAdapter<>(getActivity(), R.layout.item_home_function);
            mFunctionInfoAdapter.set(homeFunctionInfo);
            RecyclerAdapterInfo recyclerAdapterInfo = new RecyclerAdapterInfo(mFunctionInfoAdapter, LayoutManagerUtli.getGridLayoutManager(getActivity(), homeFunctionInfo.size()));
            mAdapter.add(recyclerAdapterInfo, 1);
        } else {
            mFunctionInfoAdapter.set(homeFunctionInfo);
        }
    }

    public void onError(Throwable throwable) {
        AlertToast.show(throwable.getMessage());
        showError();
    }

    public void onSuccess(List<List<GankResultsTypeInfo>> gankIoDayInfo) {
        showContent();
        if (gankIoDayInfo.size() > 0) {
            for (int i = 0; i < gankIoDayInfo.size(); i++) {
                List<GankResultsTypeInfo> gankResultsTypeInfo = gankIoDayInfo.get(i);
                mAdapter.addAll(gankResultsTypeInfo, 2);
            }
        }
        mAdapter.add(new CanInfo(), 3);
    }

    public void setImages(GankResultsTypeInfo info) {
        if (info.bannerAdapterInfo == null) {
            mSingleViewPagerAdapter = new SingleViewPagerAdapter<>(getActivity(), info.images, R.layout.item_string_page_image);
            info.bannerAdapterInfo = new BannerAdapterInfo(mSingleViewPagerAdapter);
        } else {
            mSingleViewPagerAdapter.set(info.images);
        }
    }
}
