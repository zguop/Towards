package com.waitou.towards.model.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.waitou.widget_lib.BaseDialog;
import com.waitou.wt_library.base.BasePageActivity;
import com.waitou.wt_library.view.TowardsToolbar;

/**
 * auth aboom
 * date 2019/4/6
 */
public class GloadActivity extends BasePageActivity {

    private TextView textView;

    @Override
    public void reloadData() {
        VM vm = ViewModelProviders.of(this).get(VM.class);
//        final Observer<Long> elapsedTimeObserver = new Observer<Long>() {
//            @Override
//            public void onChanged(@Nullable final Long aLong) {
//                Log.e("aa" , " along = "+ aLong);
//                textView.setText(String.valueOf(aLong));
//            }
//        };
        vm.getMap().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                textView.setText(String.valueOf(aBoolean));
            }
        });
    }

    @Override
    protected void initLoadingStatusViewIfNeed() {
        viewManager.wrapXStateController(this, false);

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        TowardsToolbar toolbar = viewManager.wrapBar();
        toolbar.initializeHeader("我是标题");
        reloadData();
    }

    @Override
    public View getContentView() {
        textView = new TextView(this);
        textView.setTextSize(16);
        textView.setTextColor(Color.BLUE);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDialog baseDialog = new BaseDialog();
                baseDialog.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
                baseDialog.setWidget(WindowManager.LayoutParams.WRAP_CONTENT);
//                        .initDialogView(new IDialogView() {
//                            @Override
//                            public View getContentView(Context activity) {
//                                TextView textView = new TextView(activity);
//                                textView.setTextColor(Color.BLUE);
//                                textView.setLayoutParams(new ViewGroup.LayoutParams(100,100));
//                                textView.setGravity(Gravity.CENTER);
//                                textView.setTextSize(16);
//                                return textView;
//                            }
//                        });
                baseDialog.show(getSupportFragmentManager());
            }
        });
        return textView;
    }

}
