package com.waitou.photopicker;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * Created by waitou on 17/4/12.
 * 图片控制器
 */

public class PhotoPickerFinal {

    private static PhotoPickerFinal sPhotoPickerFinal;

    private PhotoPickerFinal() {
    }

    public static PhotoPickerFinal get() {
        if (sPhotoPickerFinal == null) {
            synchronized (PhotoPickerFinal.class) {
                if (sPhotoPickerFinal == null) {
                    sPhotoPickerFinal = new PhotoPickerFinal();
                }
            }
        }
        return sPhotoPickerFinal;
    }

    private WeakReference<Activity> mActivityWeakReference;
    private boolean isMultiMode  = true; //图片选择模式 默认多选
    private int     selectLimit  = 9; //可以选择图片数据 默认9张
    private boolean isShowCamera = true;   //显示相机
    private boolean isCrop       = true;    //裁剪

    public PhotoPickerFinal with(Activity activity) {
        mActivityWeakReference = new WeakReference<>(activity);
        return this;
    }

    /**
     * 是否多选
     *
     * @param isMultiMode true 多选 false 单选
     */
    public PhotoPickerFinal isMultiMode(boolean isMultiMode) {
        this.isMultiMode = isMultiMode;
        return this;
    }

    /**
     * 设置最大图片选择
     */
    public PhotoPickerFinal setSelectLimit(int selectLimit) {
        this.selectLimit = selectLimit;
        return this;
    }

    /**
     * 是否显示相机拍照
     */
    public PhotoPickerFinal isShowCamera(boolean isShowCamera) {
        this.isShowCamera = isShowCamera;
        return this;
    }

    /**
     * 是否需要裁剪 单选有效
     */
    public PhotoPickerFinal isCrop(boolean isCrop) {
        this.isCrop = isCrop;
        return this;
    }

}
