package com.waitou.towards.model;

import android.os.Bundle;

import com.waitou.towards.R;
import com.waitou.towards.databinding.ActivityAslBinding;
import com.waitou.towards.model.presenter.RecommendedPresenter;

import cn.droidlover.xdroid.base.XActivity;

/**
 * Created by waitou on 17/1/9.
 */

public class AsiActivity extends XActivity<RecommendedPresenter, ActivityAslBinding> {

    @Override
    public RecommendedPresenter createPresenter() {
        return null;
    }

    @Override
    public boolean initXView() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_asl;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void reloadData() {

    }

    @Override
    public void setPresenter(RecommendedPresenter presenter) {

    }

}
