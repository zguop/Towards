package com.waitou.photo_library.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewTreeObserver;

import com.blankj.utilcode.util.UriUtils;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.waitou.imgloader_lib.DisplayOptions;
import com.waitou.imgloader_lib.ImageLoader;
import com.waitou.photo_library.R;
import com.waitou.photo_library.databinding.FragmentPhotoDetailBinding;
import com.waitou.photo_library.util.PhotoValue;
import com.waitou.wt_library.base.XFragment;
import com.waitou.wt_library.base.XPresent;

import java.io.File;

/**
 * Created by waitou on 17/4/14.
 * 查看图片的fragment
 */

public class PhotoPreviewFragment extends XFragment<XPresent, FragmentPhotoDetailBinding> {

    private String url; //url

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            url = arguments.getString(PhotoValue.EXTRA_URL);
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_photo_detail;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        getBinding().image.setOnPhotoTapListener((view, x, y) -> {
            if (getP() != null) getP().start();
        });
        reloadData();
    }

    /**
     * 使用SimpleTarget默认获取原图大小,导致viewpager滑动时,卡顿
     * 如使用ImageViewTarget默认会通过getViewTreeObserver获取view的宽高进行加载 默认隐藏布局 是不会获取到宽高值的 导致图片加载不出来
     * 这里自己手动去getViewTreeObserver获取view的宽高 通过 SimpleTarget 去指定宽高值加载图片 加载开始时显示loading 隐藏content
     */
    @Override
    public void reloadData() {
        getBinding().image.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                showLoading();
                getBinding().image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (checkHead(url)) {
                    ImageLoader.displayImage(getBinding().image, url,
                            DisplayOptions.build()
                                    .setWidth(getBinding().image.getWidth())
                                    .setHeight(getBinding().image.getHeight()),
                            new DrawableImageViewTarget(getBinding().image) {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    getBinding().image.setImageDrawable(resource);
                                    showContent();
                                }
                            });

                } else {
                    showContent();
                    getBinding().image.setImageURI(UriUtils.file2Uri(new File(url)));
                }
            }
        });
    }

    private boolean checkHead(String url) {
        return !TextUtils.isEmpty(url) && (url.startsWith("http://") || url.startsWith("https://"));
    }

    public static PhotoPreviewFragment newInstance(String imageUrl) {
        PhotoPreviewFragment fragment = new PhotoPreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PhotoValue.EXTRA_URL, imageUrl);
        fragment.setArguments(bundle);
        return fragment;
    }
}
