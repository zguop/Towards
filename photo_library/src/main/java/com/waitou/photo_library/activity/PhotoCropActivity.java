package com.waitou.photo_library.activity;

import android.os.Bundle;
import android.support.v4.content.FileProvider;

import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.waitou.photo_library.R;
import com.waitou.photo_library.databinding.ActivityPhotoCropBinding;
import com.waitou.photo_library.util.PhotoValue;
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.kit.AlertToast;
import com.waitou.wt_library.kit.UImage;

import java.io.File;

/**
 * Created by waitou on 17/4/20.
 */

public class PhotoCropActivity extends XActivity<PhotoPreviewPresenter,ActivityPhotoCropBinding> {

    private String mPhotoPath;

    @Override
    public int getContentViewId() {
        return R.layout.activity_photo_crop;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initMenuActionBar("裁剪", "完成", v -> {

        });

        mPhotoPath = getIntent().getStringExtra(PhotoValue.EXTRA_URL);

        getBinding().cropView.startLoad(FileProvider.getUriForFile(this, UImage.FILE_PROVIDER_NAME, new File(mPhotoPath)), new LoadCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                AlertToast.show("加载失败");
            }
        });
    }

    @Override
    public void reloadData() {

    }
}
