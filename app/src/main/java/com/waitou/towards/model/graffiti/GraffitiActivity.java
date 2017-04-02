package com.waitou.towards.model.graffiti;

import android.os.Bundle;

import com.waitou.towards.R;
import com.waitou.towards.databinding.ActivityGraffitiBinding;
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.kit.Util;

/**
 * Created by waitou on 17/3/19.
 * 随手涂鸦
 */

public class GraffitiActivity extends XActivity<GraffitiPresenter, ActivityGraffitiBinding> {

    @Override
    public GraffitiPresenter createPresenter() {
        return new GraffitiPresenter();
    }

    @Override
    public boolean initXView() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_graffiti;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Util.setWindowFullScreen(this, true);
        getBinding().setPresenter(getP());
    }

    @Override
    public void reloadData() {
    }
}
