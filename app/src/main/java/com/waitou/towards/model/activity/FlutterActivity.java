package com.waitou.towards.model.activity;

import android.os.Bundle;
import android.view.View;

import com.waitou.wt_library.base.BasePageActivity;

import io.flutter.facade.Flutter;

/**
 * auth aboom
 * date 2019-06-17
 */
public class FlutterActivity extends BasePageActivity {
    @Override
    public void reloadData() {

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        showContent();
    }

    @Override
    public View getContentView() {
        View flutterView = Flutter.createView(this, getLifecycle(), "route1");
        return flutterView;
    }
}
