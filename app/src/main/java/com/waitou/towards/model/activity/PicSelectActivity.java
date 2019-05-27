package com.waitou.towards.model.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.waitou.basic_lib.photo.PhotoWallImplActivity;
import com.waitou.wt_library.base.BaseActivity;
import com.waitou.wt_library.manager.RootViewManager;
import com.waitou.wt_library.manager.TitleBarManager;
import com.waitou.wt_library.view.TitleBar;

/**
 * auth aboom
 * date 2019-05-24
 */
public class PicSelectActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LinearLayout rootView = new LinearLayout(this);
//        rootView.setId(R.mediaId.page_root_view);
//        rootView.setOrientation(LinearLayout.VERTICAL);
//        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        setContentView(rootView);

        ViewGroup rootView = RootViewManager.attachViewGet(this, new LinearLayout(this));

        TitleBar titleBar = TitleBarManager.attachViewGet(this, true);
//        rootView.addView(titleBar);

        Button button = new Button(this);
        button.setText("进入相册");
        button.setOnClickListener(v -> {
            Intent i = new Intent(this, PhotoWallImplActivity.class);
            startActivity(i);

        });

        rootView.addView(button);
    }
}
