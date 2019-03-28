package com.waitou.towards.model.main.fragment.home;

import android.os.Bundle;

import com.waitou.net_library.model.Displayable;
import com.waitou.towards.R;
import com.waitou.towards.bean.GankResultsTypeInfo;
import com.waitou.towards.databinding.IncludePullRecyclerBinding;
import com.waitou.towards.databinding.ItemHeaderCargoBinding;
import com.waitou.wt_library.base.XFragment;
import com.waitou.wt_library.recycler.LayoutManagerUtil;
import com.waitou.wt_library.recycler.XRecyclerView;
import com.waitou.wt_library.recycler.adapter.SingleTypeAdapter;

import java.util.List;

/**
 * Created by waitou on 17/3/5.
 * 干货定制
 */

public class HomeCargoFragment extends XFragment<HomePresenter, IncludePullRecyclerBinding> implements XRecyclerView.OnRefreshAndLoadMoreListener {

    private SingleTypeAdapter<Displayable> mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.include_pull_recycler;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mAdapter = new SingleTypeAdapter<>(getActivity(), R.layout.item_gank_page);
        mAdapter.setPresenter(getP());
        getBinding().setManager(LayoutManagerUtil.getVerticalLayoutManager(getActivity()));
        getBinding().xList.setAdapter(mAdapter);
        getBinding().xList.getRecyclerView().useDefLoadMoreView();
        getBinding().xList.getRecyclerView().setOnRefreshAndLoadMoreListener(this);
        ItemHeaderCargoBinding headerCargoBinding = bindingInflate(R.layout.item_header_cargo, null);
        headerCargoBinding.setPresenter(getP());
        getBinding().xList.getRecyclerView().addHeaderView(headerCargoBinding.getRoot());
    }

    @Override
    protected void fragmentVisibleHint() {
        reloadData();
    }

    @Override
    public void reloadData() {
        showLoading();
        getP().loadCargoData(getP().txName.get(), 1);
    }

    public void onSuccess(List<GankResultsTypeInfo> info, boolean isClear) {
        showContent();
        if (isClear) {
            mAdapter.set(info);
        } else {
            mAdapter.addAll(info);
        }
        getBinding().xList.getRecyclerView().setDefaultPageSize();
    }

    @Override
    public void onRefresh() {
        getP().loadCargoData(getP().txName.get(), 1);
    }

    @Override
    public void onLoadMore(int page) {
        getP().loadCargoData(getP().txName.get(), page);
    }
}
