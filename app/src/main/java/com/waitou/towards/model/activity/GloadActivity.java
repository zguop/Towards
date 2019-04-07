package com.waitou.towards.model.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.waitou.wt_library.base.BasePageActivity;
import com.waitou.wt_library.view.TowardsToolbar;

/**
 * auth aboom
 * date 2019/4/6
 */
public class GloadActivity extends BasePageActivity {

    Handler handler = new Handler();

    @Override
    public void reloadData() {
        showLoading();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showFailed();
            }
        }, 2000);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        TowardsToolbar toolbar = viewManager.wrapBar();
        toolbar.initializeHeader("我是标题");
        reloadData();
    }

    @Override
    public View getContentView() {
        return new TextView(this);
    }

}
