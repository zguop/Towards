package com.waitou.towards.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.waitou.towards.R;
import com.waitou.towards.databinding.ActivityAslBinding;
import com.waitou.towards.model.activity.RecommendedPresenter;
import com.waitou.wt_library.base.XActivity;


/**
 * Created by waitou on 17/1/9.
 */

public class AsiActivity extends XActivity<RecommendedPresenter, ActivityAslBinding> {

    public static final String FRUIT_NAME     = "fruit_name";
    public static final String FRUIT_IMAGE_ID = "fruit_image_id";

    @Override
    public RecommendedPresenter createPresenter() {
        return null;
    }

    @Override
    public boolean initXView() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_asl;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        String fruitImageId = intent.getStringExtra(FRUIT_IMAGE_ID);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView fruitImageView = (ImageView) findViewById(R.id.fruit_image_view);
        TextView fruitContentText = (TextView) findViewById(R.id.fruit_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(fruitName);
        Glide.with(this).load(fruitImageId).into(fruitImageView);
        String fruitContent = generAteFruitContent(fruitName);
        fruitContentText.setText(fruitContent);
    }

    private String generAteFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void setPresenter(RecommendedPresenter presenter) {

    }

}
