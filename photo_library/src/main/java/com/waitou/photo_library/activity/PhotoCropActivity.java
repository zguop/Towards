package com.waitou.photo_library.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.to.aboomy.utils_lib.AlertToast;
import com.to.aboomy.utils_lib.UDate;
import com.to.aboomy.utils_lib.UFile;
import com.waitou.photo_library.R;
import com.waitou.photo_library.databinding.ActivityPhotoCropBinding;
import com.waitou.photo_library.util.PhotoValue;
import com.waitou.photo_library.view.ProgressDialogFragment;
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.kit.UImage;
import com.waitou.wt_library.kit.USDCard;

import java.io.File;

/**
 * Created by waitou on 17/4/20.
 * 图片裁剪页面
 */

public class PhotoCropActivity extends XActivity<PhotoCropPresenter, ActivityPhotoCropBinding> {

    private boolean isCanSave = false; // 是否可以保存
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
        getBar().initializeHeader("裁剪");
        getBar().setRightText("完成", v -> {
            if (isCanSave) {
                showProgress();
                File saveFile = UFile.getFileByPath(USDCard.getSDCardPublicPath(Environment.DIRECTORY_PICTURES) + "IMAGE_" + UDate.date2String(UDate.getNowDate(), "yyyy_MM_dd_HH_mm_ss") + UImage.JPG);
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
        getBinding().crop.startLoad(FileProvider.getUriForFile(this, UImage.FILE_PROVIDER_NAME, new File(photoPath)), new LoadCallback() {
            @Override
            public void onSuccess() {
                showContent();
                isCanSave = true;
            }

            @Override
            public void onError() {
                AlertToast.show("加载失败");
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
