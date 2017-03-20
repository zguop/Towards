package com.waitou.towards.model.graffiti;

import android.os.Bundle;

import com.waitou.towards.R;
import com.waitou.wt_library.base.XActivity;

/**
 * Created by waitou on 17/3/19.
 * 随手涂鸦
 */

public class GraffitiActivity extends XActivity {

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

    }

    @Override
    public void reloadData() {

    }
}
