package com.waitou.imgloader_lib;


import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * auth aboom
 * date 2018/8/13
 *
 * ####这个是为了替换fresco方便创建的view，不建议直接使用
 * 圆形圆角使用建议使用 HsRoundImageView
 * 通过ImageLoader.class工具类加载图片
 */
public class HsImageLoaderView extends HsRoundImageView {

    private DisplayOptions displayOptions;

    public HsImageLoaderView(Context context) {
        this(context, null);
    }

    public HsImageLoaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HsImageLoaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HsImageLoaderView);
        try {
            int placeholderImageId = a.getResourceId(R.styleable.HsImageLoaderView_hs_placeholder_image, DisplayOptions.RES_NONE);
            int failureImageId = a.getResourceId(R.styleable.HsImageLoaderView_hs_failure_image, DisplayOptions.RES_NONE);
            displayOptions = DisplayOptions.build().setPlaceholder(placeholderImageId).setError(failureImageId);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        loadImage(uri);
    }

    public <T> void loadImage(@Nullable T modelLoader) {
        ImageLoader.displayImage(this, modelLoader, displayOptions);
    }

    public void clearImage() {
        ImageLoader.clearImage(this);
    }
}
