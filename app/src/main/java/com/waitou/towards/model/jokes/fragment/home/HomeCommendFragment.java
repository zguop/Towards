package com.waitou.towards.model.jokes.fragment.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.waitou.towards.R;
import com.waitou.towards.databinding.FragmentHomeBinding;
import com.waitou.towards.model.jokes.contract.MainContract;
import com.waitou.wt_library.base.XFragment;

import java.util.Arrays;
import java.util.List;


/**
 * Created by waitou on 17/2/10.
 */

public class HomeCommendFragment extends XFragment<MainContract.MainPresenter, FragmentHomeBinding> {

    private MainContract.MainPresenter mPresenter;

    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",

    };

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
        List<String> strings = Arrays.asList(images);
        BannerAdapter adapter = new BannerAdapter(strings);
        getBinding().banner.setAdapter(adapter);
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
