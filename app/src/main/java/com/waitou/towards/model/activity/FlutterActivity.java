package com.waitou.towards.model.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.waitou.wt_library.base.BaseActivity;

import io.flutter.facade.Flutter;

/**
 * auth aboom
 * date 2019-06-17
 */
public class FlutterActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View flutterView = Flutter.createView(this, getLifecycle(), "route1");
        setContentView(flutterView);
    }
}
