package com.waitou.photo_library.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.to.aboomy.rx_lib.RxBus;
import com.to.aboomy.utils_lib.AlertToast;
import com.to.aboomy.utils_lib.USize;
import com.waitou.photo_library.PhotoPickerFinal;
import com.waitou.photo_library.R;
import com.waitou.photo_library.bean.PhotoInfo;
import com.waitou.photo_library.databinding.ActivityPhotoWallBinding;
import com.waitou.photo_library.event.PhotoEvent;
import com.waitou.photo_library.util.PhotoValue;
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.recycler.LayoutManagerUtil;
import com.waitou.wt_library.recycler.adapter.SingleTypeAdapter;
import com.waitou.wt_library.recycler.divider.GridSpacingItemDecoration;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by waitou on 17/4/3.
 * 选择图片页面
 */

public class PhotoWallActivity extends XActivity<PhotoWallPresenter, ActivityPhotoWallBinding> {

    public static final int PHOTO_REQUEST_CODE   = 0;
    public static final int PREVIEW_REQUEST_CODE = 1;
    public static final int CROP_REQUEST_CODE    = 2;

    private SingleTypeAdapter<PhotoInfo> mPhotoGridAdapter;
    private PhotoPickerFinal             mPhotoPickerFinal;

    private TextView mRightText;

    @Override
    public PhotoWallPresenter createPresenter() {
        return new PhotoWallPresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_photo_wall;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mPhotoPickerFinal = PhotoPickerFinal.get();
        getBar().initializeHeader("选择图片");
        if (mPhotoPickerFinal.isMultiMode()) {
            mRightText = getBar().setRightText("完成", v -> submit());
            setRightText();
        }
        getBinding().setPresenter(getP());
        getBinding().setIsMultiMode(mPhotoPickerFinal.isMultiMode());
        mPhotoGridAdapter = new SingleTypeAdapter<>(this, R.layout.item_photo);
        mPhotoGridAdapter.setPresenter(getP());
        getBinding().xList.setLayoutManager(LayoutManagerUtil.getGridLayoutManager(this, 3));
        getBinding().xList.setAdapter(mPhotoGridAdapter);
        getBinding().xList.addItemDecoration(new GridSpacingItemDecoration(3, USize.dip2pxInt(2), false));
        reloadData();
    }


    public void submit() {
        RxBus.getDefault().post(new PhotoEvent(getP().selectionList));
        finish();
    }

    public void setRightText() {
        mRightText.setText("完成(" + getP().preview.get() + "/" + mPhotoPickerFinal.getSelectLimit() + ")");
    }

    @Override
    public void reloadData() {
        getRxPermissions().requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        getP().imageDataSource(null);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        AlertToast.show("权限被禁止，无法选择本地图片！"); //拒绝了权限
                    } else {
                        AlertToast.show("请到应用设置中开启权限！");//永久拒绝了权限
                    }
                });
    }

    public void onPhotoLoaded(ArrayList<PhotoInfo> list) {
        mPhotoGridAdapter.set(list);
        getBinding().xList.smoothScrollToPosition(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_REQUEST_CODE: //相机拍照回调
                    getP().takePictureResult(null);
                    break;
                case PREVIEW_REQUEST_CODE://预览图片回调
                    getP().selectionList.clear();
                    getP().selectionList.addAll(data.getParcelableArrayListExtra(PhotoValue.EXTRA_PHOTO_ITEMS));
                    getP().refreshFooterPreviewUI();
                    if (data.getBooleanExtra(PhotoValue.EXTRA_IS_COMMIT, false)) {
                        submit();
                    }
                    break;
                case CROP_REQUEST_CODE:
                    File photoInfo = (File) data.getSerializableExtra(PhotoValue.EXTRA_CROP_PHOTO);
                    getP().takePictureResult(photoInfo);
                    break;
            }
        }
    }
}
