package com.waitou.photo_library.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.widget.CompoundButton;

import com.waitou.photo_library.PhotoPickerFinal;
import com.waitou.photo_library.bean.PhotoInfo;
import com.waitou.photo_library.util.PhotoValue;
import com.waitou.wt_library.base.XPresent;
import com.to.aboomy.utils_lib.AlertToast;

import java.util.List;

/**
 * Created by waitou on 17/4/14.
 * 预览图片
 */

public class PhotoPreviewPresenter extends XPresent<PhotoPreviewActivity> {

    public PhotoPickerFinal mPhotoPickerFinal;
    public List<PhotoInfo>  mPhotoList; //所以图片
    public ObservableInt                  position        = new ObservableInt(); //当前的position
    public ObservableArrayList<PhotoInfo> selectPhotoList = new ObservableArrayList<>(); //选中图片集合
    public ObservableBoolean              barVisibility   = new ObservableBoolean(true);//是否隐藏显示bar

    PhotoPreviewPresenter() {
        mPhotoPickerFinal = PhotoPickerFinal.get();
        mPhotoList = mPhotoPickerFinal.getPhotoList();
    }

    public void onCheckBoxChange(CompoundButton compoundButton, boolean isChecked) {
        PhotoInfo photoInfo = mPhotoList.get(position.get());
        if (isChecked) {
            if (!selectPhotoList.contains(photoInfo)) {
                if (selectPhotoList.size() == mPhotoPickerFinal.getSelectLimit()) {
                    compoundButton.setChecked(false);
                    AlertToast.show("最多选择" + mPhotoPickerFinal.getSelectLimit() + "张图片");
                    return;
                }
                selectPhotoList.add(photoInfo);
            }
        } else {
            if (selectPhotoList.contains(photoInfo)) {
                selectPhotoList.remove(photoInfo);
            }
        }
    }

    public void onBack(boolean isCommit) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(PhotoValue.EXTRA_PHOTO_ITEMS, selectPhotoList);
        intent.putExtra(PhotoValue.EXTRA_IS_COMMIT, isCommit);
        getV().setResult(Activity.RESULT_OK, intent);
        getV().finish();
    }

    @Override
    public void start() {
        barVisibility.set(!barVisibility.get());
    }
}
