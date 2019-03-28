package com.waitou.towards.model.main.fragment;

import android.os.Bundle;

import com.waitou.towards.R;
import com.waitou.towards.bean.TitleInfo;
import com.waitou.towards.databinding.FragmentHomeBinding;
import com.waitou.wt_library.base.XFragment;
import com.waitou.wt_library.base.XPresent;

/**
 * Created by waitou on 16/12/23.
 */

public class CircleFragment extends XFragment<XPresent, FragmentHomeBinding> {

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        TitleInfo homeinfo = new TitleInfo("sss");
        getBinding().setItem(homeinfo);
    }

    @Override
    public void reloadData() {

    }

}
