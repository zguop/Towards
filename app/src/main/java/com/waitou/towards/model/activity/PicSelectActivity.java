package com.waitou.towards.model.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.waitou.basic_lib.photo.PhotoWallImplActivity;
import com.waitou.basic_lib.photo.view.CheckView;
import com.waitou.photopicker.Wisdom;
import com.waitou.photopicker.bean.ResultMedia;
import com.waitou.towards.R;
import com.waitou.wt_library.base.BaseActivity;

import java.util.List;

/**
 * auth aboom
 * date 2019-05-24
 */
public class PicSelectActivity extends BaseActivity {
    private boolean isCamera = true;
    boolean isCheck = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        CheckBox checkBox = findViewById(R.id.camera);
        EditText num = findViewById(R.id.num);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCamera = isChecked;
            }
        });

        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wisdom.of(PicSelectActivity.this)
                        .config()
                        .imageEngine(new GlideEngine())
                        .selectLimit(Integer.valueOf(num.getText().toString()))
                        .fileProvider(getPackageName() + ".utilcode.provider", "image")
                        .isCamera(isCamera)
                        .forResult(0x11, PhotoWallImplActivity.class);
            }
        });


        CheckView checkView = findViewById(R.id.check1);

        checkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheck = !isCheck;
                checkView.setCheckedNum(isCheck ? 1 : Integer.MAX_VALUE);


                checkView.toggle();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            if (requestCode == 0x11 && data != null) {
                List<ResultMedia> resultMedia = Wisdom.obtainResult(data);
                Log.e("aa", resultMedia.toString());

            }
        }
    }
}
