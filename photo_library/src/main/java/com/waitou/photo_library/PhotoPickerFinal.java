package com.waitou.photo_library;

import android.app.Activity;
import android.os.Parcelable;

import com.blankj.utilcode.util.ObjectUtils;
import com.to.aboomy.rx_lib.RxBus;
import com.waitou.photo_library.activity.PhotoPreviewActivity;
import com.waitou.photo_library.activity.PhotoWallActivity;
import com.waitou.photo_library.bean.PhotoInfo;
import com.waitou.photo_library.event.PhotoEvent;
import com.waitou.photo_library.util.PhotoValue;
import com.waitou.wt_library.router.Router;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
    private Disposable              rx;
    private boolean isMultiMode  = true; //图片选择模式 默认多选
    private int     selectLimit  = 9; //可以选择图片数据 默认9张
    private boolean isShowCamera = true;   //显示相机
    private boolean isCrop       = true;    //裁剪
    private List<PhotoInfo> photoList; //所要预览的图片集合

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

    /**
     * 预览图片需设置图片
     */
    public PhotoPickerFinal setPhotoList(List<PhotoInfo> photoList) {
        this.photoList = photoList;
        return this;
    }

    /**
     * 预览图片需设置图片
     */
    public PhotoPickerFinal setStrPhotoList(List<String> url) {
        ArrayList<PhotoInfo> photoList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(url)) {
            for (String s : url) {
                PhotoInfo photoInfo = new PhotoInfo();
                photoInfo.photoPath = s;
                photoList.add(photoInfo);
            }
        }
        this.photoList = photoList;
        return this;
    }

    /**
     * 预览图片需设置图片
     */
    public PhotoPickerFinal setStrPhotoList(String... url) {
        ArrayList<PhotoInfo> photoList = new ArrayList<>();
        for (String s : url) {
            PhotoInfo photoInfo = new PhotoInfo();
            photoInfo.photoPath = s;
            photoList.add(photoInfo);
        }
        this.photoList = photoList;
        return this;
    }

    /**
     * 进入到图片选择列表
     *
     * @param action 选择后的图片回调
     */
    public Disposable executePhoto(Consumer<List<PhotoInfo>> action) {
        if (rx != null && !rx.isDisposed()) {
            rx.dispose();
        }
        if (action != null) {
            rx = RxBus.getDefault().toObservable(PhotoEvent.class)
                    .subscribe(photoEvent -> action.accept(photoEvent.getSelectionList()));
        }
        if (mActivityWeakReference != null && mActivityWeakReference.get() != null) {
            Router.newIntent().from(mActivityWeakReference.get()).to(PhotoWallActivity.class).launch();
        }
        return rx;
    }

    /**
     * 进入到图片预览页面
     */
    public void executePreViewPhoto() {
        if (mActivityWeakReference != null && mActivityWeakReference.get() != null) {
            Router.newIntent()
                    .from(mActivityWeakReference.get())
                    .to(PhotoPreviewActivity.class)
                    .putBoolean(PhotoValue.EXTRA_IS_PREVIEW, true)
                    .putParcelableArrayList(PhotoValue.EXTRA_PHOTO_ITEMS, (ArrayList<? extends Parcelable>) photoList)
                    .launch();
        }
    }

    public boolean isMultiMode() {
        return isMultiMode;
    }

    public int getSelectLimit() {
        return selectLimit;
    }

    public boolean isShowCamera() {
        return isShowCamera;
    }

    public boolean isCrop() {
        return isCrop;
    }

    public List<PhotoInfo> getPhotoList() {
        return photoList;
    }
}
