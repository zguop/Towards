package com.waitou.photo_library.activity;

import android.databinding.ObservableField;

import com.isseiaoki.simplecropview.CropImageView;
import com.waitou.wt_library.base.XPresent;

/**
 * Created by waitou on 17/4/20.
 */

public class PhotoCropPresenter extends XPresent<PhotoCropActivity> {

    public ObservableField<CropImageView.CropMode> mCropMode = new ObservableField<>(CropImageView.CropMode.FIT_IMAGE);

    public void setCropMode() {
        if (mCropMode.get() == CropImageView.CropMode.FIT_IMAGE) {
            mCropMode.set(CropImageView.CropMode.SQUARE);
        } else {
            mCropMode.set(CropImageView.CropMode.FIT_IMAGE);
        }
    }

    public void setCircleCropMode() {
        mCropMode.set(CropImageView.CropMode.CIRCLE);
    }

    public void setLeftRotateImage(CropImageView cropImageView){
        cropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
    }

    public void setRightRotateImage(CropImageView cropImageView){
        cropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
    }
}
