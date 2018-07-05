package com.waitou.towards.model.graffiti;

import android.os.Bundle;

import com.to.aboomy.utils_lib.Util;
import com.waitou.towards.R;
import com.waitou.towards.databinding.ActivityGraffitiBinding;
import com.waitou.towards.view.dialog.BaseDialog;
import com.waitou.wt_library.base.XActivity;

/**
 * Created by waitou on 17/3/19.
 * 随手涂鸦
 */

public class GraffitiActivity extends XActivity<GraffitiPresenter, ActivityGraffitiBinding> {

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
        Util.setWindowFullScreen(this, true);
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
}
