package com.waitou.towards.model.theme;

import android.graphics.Color;
import android.os.Bundle;

import com.waitou.towards.R;
import com.waitou.towards.bean.ThemeInfo;
import com.waitou.towards.databinding.IncludeMatchRecyclerViewBinding;
import com.waitou.towards.model.event.ThemeEvent;
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.recycler.LayoutManagerUtli;
import com.waitou.wt_library.recycler.adapter.SingleTypeAdapter;
import com.waitou.wt_library.rx.RxBus;


/**
 * Created by waitou on 17/1/16.
 */

public class ThemeActivity extends XActivity<ThemePresenter, IncludeMatchRecyclerViewBinding> {

    private SingleTypeAdapter<ThemeInfo> mAdapter;
    private ThemeInfo                    info;

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
        return R.layout.include_match_recycler_view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initMenuActionBar("主题", "完成", v -> {
            RxBus.getDefault().post(new ThemeEvent(info));
            finish();
        });
        mAdapter = new SingleTypeAdapter<>(this, R.layout.item_theme);
        mAdapter.setPresenter(getP());
        getBinding().setManager(LayoutManagerUtli.getGridLayoutManager(this,3));
        getBinding().setAdapter(mAdapter);
        getBinding().xList.setBackgroundColor(Color.WHITE);
        reloadData();
    }

    @Override
    public void reloadData() {
        mAdapter.set(getP().loadData());
    }

    public void theme(ThemeInfo info) {
        this.info = info;
    }
}
