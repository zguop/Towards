package com.waitou.towards.model.jokes.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.markzhai.recyclerview.SingleTypeAdapter;
import com.waitou.towards.R;
import com.waitou.towards.bean.ThemeInfo;
import com.waitou.towards.databinding.IncludeRecyclerViewBinding;
import com.waitou.towards.model.event.DrawerToggleEvent;
import com.waitou.towards.model.presenter.ThemePresenter;

import cn.droidlover.xdroid.base.XActivity;
import cn.droidlover.xdroid.rx.RxBus;

/**
 * Created by waitou on 17/1/16.
 */

public class ThemeActivity extends XActivity<ThemePresenter, IncludeRecyclerViewBinding> {

    private SingleTypeAdapter<ThemeInfo> mAdapter;
    private ThemeInfo info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public ThemePresenter createPresenter() {
        return new ThemePresenter();
    }

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
        initMenuActionBar("主题", "完成", v -> {
            RxBus.getDefault().post(new DrawerToggleEvent(info));
            finish();
        });
        mAdapter = new SingleTypeAdapter<>(this, R.layout.item_theme);
        mAdapter.setPresenter(getP());
        getBinding().xList.gridLayoutManager(this, 3).setAdapter(mAdapter);
        reloadData();
    }

    @Override
    public void reloadData() {
        mAdapter.set(getP().loadData());
    }

    @Override
    public void setPresenter(ThemePresenter presenter) {
    }

    public void theme(ThemeInfo info) {
        this.info = info;
    }
}
