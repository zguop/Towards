package com.waitou.photo_library.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.waitou.photo_library.R;
import com.waitou.photo_library.bean.PhotoInfo;
import com.waitou.photo_library.databinding.ActivityPhotoPreviewBinding;
import com.waitou.photo_library.fragment.PhotoPreviewFragment;
import com.waitou.photo_library.util.PhotoValue;
import com.waitou.wt_library.base.XActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waitou on 17/4/14.
 * 预览图片界面
 */

public class PhotoPreviewActivity extends XActivity<PhotoPreviewPresenter, ActivityPhotoPreviewBinding> {

    @Override
    public boolean defaultXView() {
        return false;
    }

    @Override
    public PhotoPreviewPresenter createPresenter() {
        return new PhotoPreviewPresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_photo_preview;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        transparentStatusBar(this);
        Intent intent = getIntent();
        int position = intent.getIntExtra(PhotoValue.EXTRA_SELECTED_PHOTO_POSITION, 0);
        ArrayList<PhotoInfo> selectPhoto = intent.getParcelableArrayListExtra(PhotoValue.EXTRA_PHOTO_ITEMS);
        getBinding().setIsPreview(intent.getBooleanExtra(PhotoValue.EXTRA_IS_PREVIEW, false));
        PhotoPagerAdapter adapter = new PhotoPagerAdapter(getSupportFragmentManager(), getP().mPhotoPickerFinal.getPhotoList());
        getBinding().pager.setAdapter(adapter);
        getBinding().setPresenter(getP());
        getP().selectPhotoList.addAll(selectPhoto);
        getP().position.set(position);
    }

    @Override
    public void reloadData() {
    }

    private class PhotoPagerAdapter extends FragmentStatePagerAdapter {

        private List<PhotoInfo> data;

        PhotoPagerAdapter(FragmentManager fm, List<PhotoInfo> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {
            PhotoPreviewFragment photoPreviewFragment = PhotoPreviewFragment.newInstance(data.get(position).photoPath);
            photoPreviewFragment.setPresenter(getP());
            return photoPreviewFragment;
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }
    }

    @Override
    public void onBackPressed() {
        getP().onBack(false);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
