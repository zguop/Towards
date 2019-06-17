package com.waitou.towards.model.graffiti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.to.aboomy.statusbar_lib.StatusBarUtil;
import com.waitou.basic_lib.util.GlideEngine;
import com.waitou.towards.R;
import com.waitou.towards.databinding.ActivityGraffitiBinding;
import com.waitou.towards.view.dialog.BaseDialog;
import com.waitou.wisdom_impl.ui.PhotoWallActivity;
import com.waitou.wisdom_lib.Wisdom;
import com.waitou.wisdom_lib.bean.Media;
import com.waitou.wisdom_lib.config.MimeTypeKt;
import com.waitou.wt_library.base.XActivity;

import java.util.List;

/**
 * Created by waitou on 17/3/19.
 * 随手涂鸦
 */

public class GraffitiActivity extends XActivity<GraffitiPresenter, ActivityGraffitiBinding> {

    public static final int PHOTO_REQUEST = 0x01;

    @Override
    public GraffitiPresenter createPresenter() {
        return new GraffitiPresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_graffiti;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        StatusBarUtil.transparencyBar(this, true);
        getBinding().setPresenter(getP());
    }

    @Override
    public void reloadData() {
    }

    @Override
    public void onBackPressed() {
        if (getBinding().graffiti.checkSave() || getBinding().pic.checkSave()) {
            new BaseDialog(this)
                    .setContent("确定要退出么")
                    .setCancel("取消", null)
                    .setOk("确定", this::finish)
                    .setDCanceledOnTouchOutside(false)
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    public void startPhotoPicker() {
        Wisdom.of(this)
                .config(MimeTypeKt.ofImage())
                .isCamera(false)
                .selectLimit(1)
                .imageEngine(new GlideEngine())
                .forResult(PHOTO_REQUEST, PhotoWallActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK != resultCode || data == null) {
            return;
        }
        if (PHOTO_REQUEST == requestCode) {
            List<Media> media = Wisdom.obtainResult(data);
            getP().uploadPicResult(media.get(0));
        }

    }
}
