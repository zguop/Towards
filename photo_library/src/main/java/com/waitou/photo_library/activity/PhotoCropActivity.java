package com.waitou.photo_library.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UriUtils;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.waitou.photo_library.R;
import com.waitou.photo_library.databinding.ActivityPhotoCropBinding;
import com.waitou.photo_library.util.PhotoValue;
import com.waitou.photo_library.view.ProgressDialogFragment;
import com.waitou.wt_library.base.XActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by waitou on 17/4/20.
 * 图片裁剪页面
 */

public class PhotoCropActivity extends XActivity<PhotoCropPresenter, ActivityPhotoCropBinding> {

    private boolean                isCanSave = false; // 是否可以保存
    private ProgressDialogFragment mDialogFragment;

    @Override
    public PhotoCropPresenter createPresenter() {
        return new PhotoCropPresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_photo_crop;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        getBar().setVisibility(View.VISIBLE);
        getBar().initializeHeader("裁剪");
        getBar().setRightText("完成", v -> {
            if (isCanSave) {
                showProgress();
                File saveFile = FileUtils.getFileByPath(PathUtils.getExternalPicturesPath() + File.separator +
                        "IMAGE_" + TimeUtils.date2String(TimeUtils.getNowDate(), new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())) + ".jpg");
                getBinding().crop.startCrop(Uri.fromFile(saveFile), new CropCallback() {
                    @Override
                    public void onSuccess(Bitmap cropped) {
                    }

                    @Override
                    public void onError() {
                        hideProgress();
                    }
                }, new SaveCallback() {
                    @Override
                    public void onSuccess(Uri outputUri) {
                        Intent intent = new Intent();
                        intent.putExtra(PhotoValue.EXTRA_CROP_PHOTO, saveFile);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onError() {
                        hideProgress();
                    }
                });
            }
        });
        getBinding().setPresenter(getP());
        String photoPath = getIntent().getStringExtra(PhotoValue.EXTRA_URL);
        getBinding().crop.startLoad( UriUtils.file2Uri(new File(photoPath)), new LoadCallback() {
            @Override
            public void onSuccess() {
                showContent();
                isCanSave = true;
            }

            @Override
            public void onError() {
                ToastUtils.showShort("加载失败");
                finish();
            }
        });
    }


    @Override
    public void reloadData() {
    }

    private void showProgress() {
        if (mDialogFragment == null) {
            mDialogFragment = new ProgressDialogFragment();
        }
        mDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);
    }

    private void hideProgress() {
        if (mDialogFragment != null) mDialogFragment.dismissAllowingStateLoss();
    }
}
