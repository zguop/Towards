package com.waitou.towards.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.waitou.towards.R;
import com.waitou.towards.databinding.ActivityThemeBinding;

import cn.droidlover.xdroid.base.XActivity;
import cn.droidlover.xdroid.base.XPresent;

/**
 * Created by waitou on 17/1/16.
 */

public class ThemeActivity extends XActivity<XPresent, ActivityThemeBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        ChangeModeController.get().init(this);
        super.onCreate(savedInstanceState);
    }

    private boolean is;

    @Override
    public XPresent createPresenter() {
        return null;
    }

    @Override
    public boolean initXView() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_theme;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getBinding().btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is = !is;
                if(is){


                }else {
                }

            }
        });
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void setPresenter(XPresent presenter) {

    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(ThemeActivity.this,MainActivity.class);
        startActivity(intent);
        super.onDestroy();


    }
}
