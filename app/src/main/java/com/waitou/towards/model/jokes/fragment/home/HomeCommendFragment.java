package com.waitou.towards.model.jokes.fragment.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.waitou.towards.R;
import com.waitou.towards.databinding.FragmentHomeBinding;
import com.waitou.towards.model.jokes.contract.MainContract;

import cn.droidlover.xdroid.base.XFragment;

/**
 * Created by waitou on 17/2/10.
 */

public class HomeCommendFragment extends XFragment<MainContract.MainPresenter, FragmentHomeBinding> {

    private MainContract.MainPresenter mPresenter;

    @Override
    public boolean initXView() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    @Override
    public void reloadData() {

    }

    @Override
    public void setPresenter(MainContract.MainPresenter presenter) {
        this.mPresenter = presenter;
    }

    public static Fragment getInstance(MainContract.MainPresenter presenter) {
        HomeCommendFragment fragment = new HomeCommendFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }
}
