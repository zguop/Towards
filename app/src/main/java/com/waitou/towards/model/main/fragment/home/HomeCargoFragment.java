package com.waitou.towards.model.main.fragment.home;

import android.os.Bundle;

import com.waitou.net_library.model.Displayable;
import com.waitou.towards.R;
import com.waitou.towards.bean.GankResultsTypeInfo;
import com.waitou.towards.databinding.IncludePullRecyclerBinding;
import com.waitou.towards.databinding.ItemHeaderCargoBinding;
import com.waitou.wt_library.base.XFragment;
import com.waitou.wt_library.recycler.LayoutManagerUtli;
import com.waitou.wt_library.recycler.adapter.MultiTypeAdapter;

import java.util.List;

/**
 * Created by waitou on 17/3/5.
 * 干货定制
 */

public class HomeCargoFragment extends XFragment<HomePresenter, IncludePullRecyclerBinding> {

    private MultiTypeAdapter<Displayable> mAdapter;
    private ItemHeaderCargoBinding        mHeaderCargoBinding;

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
        mAdapter = new MultiTypeAdapter<>(getActivity());
        getBinding().setManager(LayoutManagerUtli.getVerticalLayoutManager(getActivity()));
        getBinding().setAdapter(mAdapter);
    }

    @Override
    protected void fragmentVisibleHint() {
        reloadData();
    }

    @Override
    public void reloadData() {
        showLoading();
        getP().loadCargoData("all", 1);
    }

    public void onSuccess(List<GankResultsTypeInfo> info, boolean isClear) {
        showContent();
        if (mHeaderCargoBinding == null) {
            mHeaderCargoBinding = (ItemHeaderCargoBinding) bindingInflate(R.layout.item_header_cargo, null);
            mHeaderCargoBinding.setPresenter(getP());
            getBinding().xList.getRecyclerView().addHeaderView(mHeaderCargoBinding.getRoot());
        }
        if (isClear) {
//            mAdapter.set(info, 0);
        } else {
//            mAdapter.addAll(info, 0);
        }
    }

}
