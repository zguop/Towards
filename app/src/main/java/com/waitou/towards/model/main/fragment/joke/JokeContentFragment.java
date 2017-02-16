package com.waitou.towards.model.main.fragment.joke;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.waitou.towards.ExtraValue;
import com.waitou.towards.R;
import com.waitou.towards.bean.JokeInfo;
import com.waitou.towards.databinding.IncludePullRecyclerBinding;
import com.waitou.towards.model.main.contract.MainContract;
import com.waitou.wt_library.base.XFragment;
import com.waitou.wt_library.recycler.XRecyclerView;
import com.waitou.wt_library.recycler.adapter.MultiTypeAdapter;

import java.util.List;


/**
 * Created by waitou on 17/1/10.
 */

public class JokeContentFragment extends XFragment<MainContract.MainPresenter, IncludePullRecyclerBinding> implements XRecyclerView.OnRefreshAndLoadMoreListener, MainContract.JokeContentView<JokeInfo> {

    private int                        mType;
    private MultiTypeAdapter<JokeInfo>           mAdapter;
    private MainContract.MainPresenter mPresenter;

    @Override
    public boolean initXView() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.include_pull_recycler;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mType = getArguments().getInt(ExtraValue.JOKE_CONTENT_TYPE);
        mAdapter = new MultiTypeAdapter<>(getActivity());
        mAdapter.addViewTypeToLayoutMap(0, R.layout.item_textjoke);
        mAdapter.addViewTypeToLayoutMap(1, R.layout.item_imagejoke);
        getBinding().xList.getRecyclerView().verticalLayoutManager(getActivity()).setAdapter(mAdapter);
        getBinding().xList.getRecyclerView().useDefLoadMoreView();
        getBinding().xList.getRecyclerView().setOnRefreshAndLoadMoreListener(this);
    }

    @Override
    protected boolean fragmentVisibleHint() {
        reloadData();
        return true;
    }

    @Override
    public void reloadData() {
        showLoading();
        mPresenter.loadJokeData(1, mType);
    }

    @Override
    public void onRefresh() {
        mPresenter.loadJokeData(1, mType);
    }

    @Override
    public void onLoadMore(int page) {
        mPresenter.loadJokeData(page, mType);
    }

    @Override
    public void showError(boolean isReload) {
        super.showError(isReload);
    }

    @Override
    public void success(int page, List<JokeInfo> info) {
        if (page == 1) {
            mAdapter.clear();
            if (info == null || info.size() == 0) {
                showEmpty();
                return;
            }
        }
        mAdapter.addAll(info, mType);
        getBinding().xList.getRecyclerView().setDefaultPageSize();
    }

    public static Fragment getInstance(int type, MainContract.MainPresenter presenter) {
        JokeContentFragment fragment = new JokeContentFragment();
        fragment.setPresenter(presenter);
        Bundle bundle = new Bundle();
        bundle.putInt(ExtraValue.JOKE_CONTENT_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setPresenter(MainContract.MainPresenter presenter) {
        this.mPresenter = presenter;
    }
}

