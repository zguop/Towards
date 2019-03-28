package com.waitou.towards.model.main.fragment.joke;

import android.os.Bundle;

import com.waitou.towards.R;
import com.waitou.towards.bean.JokeInfo;
import com.waitou.towards.common.ExtraValue;
import com.waitou.towards.databinding.IncludePullRecyclerBinding;
import com.waitou.wt_library.base.XFragment;
import com.waitou.wt_library.recycler.LayoutManagerUtil;
import com.waitou.wt_library.recycler.XRecyclerView;
import com.waitou.wt_library.recycler.adapter.MultiTypeAdapter;

import java.util.List;


/**
 * Created by waitou on 17/1/10.
 */

public class JokeContentFragment extends XFragment<TextJokePresenter, IncludePullRecyclerBinding> implements XRecyclerView.OnRefreshAndLoadMoreListener {

    private int                        mType;
    private MultiTypeAdapter<JokeInfo> mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.include_pull_recycler;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mType = getArguments().getInt(ExtraValue.JOKE_CONTENT_TYPE);
        mAdapter = new MultiTypeAdapter<>(getActivity());
        mAdapter.addViewTypeToLayoutMap(0, R.layout.item_textjoke);
        mAdapter.addViewTypeToLayoutMap(1, R.layout.item_imagejoke);
        mAdapter.setPresenter(getP());
        getBinding().setManager(LayoutManagerUtil.getVerticalLayoutManager(getActivity()));
        getBinding().xList.setAdapter(mAdapter);
        getBinding().xList.getRecyclerView().useDefLoadMoreView();
        getBinding().xList.getRecyclerView().setOnRefreshAndLoadMoreListener(this);
    }

    @Override
    protected void fragmentVisibleHint() {
        reloadData();
    }

    @Override
    public void reloadData() {
        showLoading();
        getP().loadJokeData(1, mType);
    }

    @Override
    public void onRefresh() {
        getP().loadJokeData(1, mType);
    }

    @Override
    public void onLoadMore(int page) {
        getP().loadJokeData(page, mType);
    }

    @Override
    public void showError(boolean isReload) {
        super.showError(isReload);
    }

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
}

