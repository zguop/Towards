package com.waitou.photo_library.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewTreeObserver;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.to.aboomy.utils_lib.UString;
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

    private String  url; //url

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
                    Glide.with(getActivity()).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<GlideDrawable>(getBinding().image.getWidth(), getBinding().image.getHeight()) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            showContent();
                            getBinding().image.setImageDrawable(resource);
                        }
                    });
                } else {
                    Glide.with(getActivity()).load(new File(url)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<GlideDrawable>(getBinding().image.getWidth(), getBinding().image.getHeight()) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            showContent();
                            getBinding().image.setImageDrawable(resource);
                        }
                    });
                }
            }
        });
    }

    private boolean checkHead(String url) {
        return UString.isNotEmpty(url) && (url.startsWith("http://") || url.startsWith("https://"));
    }

    public static PhotoPreviewFragment newInstance(String imageUrl) {
        PhotoPreviewFragment fragment = new PhotoPreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PhotoValue.EXTRA_URL, imageUrl);
        fragment.setArguments(bundle);
        return fragment;
    }
}
