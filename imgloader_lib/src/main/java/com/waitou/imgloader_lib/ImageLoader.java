package com.waitou.imgloader_lib;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

/**
 * auth aboom
 * date 2018/8/8
 */
public class ImageLoader {

    /**
     * 通用的加载图片的方法
     *
     * @param imageView   加载的view
     * @param modelLoader glide load方法支持的所有类型
     */
    public static <T> void displayImage(ImageView imageView, T modelLoader) {
        Glide.with(imageView)
                .load(modelLoader)
                .transition(DrawableTransitionOptions.with(crossFade()))
                .into(imageView);
    }

    /**
     * 通用的加载图片的方法
     *
     * @param imageView   加载的view
     * @param modelLoader glide load方法支持的所有类型
     * @param placeholder 占位图
     */
    public static <T> void displayImage(ImageView imageView, T modelLoader, int placeholder) {
        Glide.with(imageView)
                .load(modelLoader)
                .transition(DrawableTransitionOptions.with(crossFade()))
                .apply(getRequestOptions(DisplayOptions.build().setPlaceholder(placeholder)))
                .into(imageView);
    }

    /**
     * 通用的加载图片的方法
     *
     * @param imageView   加载的view
     * @param modelLoader glide load方法支持的所有类型
     * @param options     配置属性
     */
    public static <T> void displayImage(ImageView imageView, T modelLoader, DisplayOptions options) {
        Glide.with(imageView)
                .load(modelLoader)
                .transition(DrawableTransitionOptions.with(crossFade()))
                .apply(getRequestOptions(options))
                .into(imageView);
    }

    /**
     * 图片加载方法回调加载
     */
    public static <T> void displayImage(ImageView imageView, T modelLoader, BitmapImageViewTarget target) {
        Glide.with(imageView)
                .asBitmap()
                .load(modelLoader)
                .transition(BitmapTransitionOptions.withCrossFade(crossFade()))
                .into(target);
    }

    /**
     * 图片加载方法回调加载
     */
    public static <T> void displayImage(ImageView imageView, T modelLoader, int placeholder, BitmapImageViewTarget target) {
        Glide.with(imageView)
                .asBitmap()
                .load(modelLoader)
                .transition(BitmapTransitionOptions.withCrossFade(crossFade()))
                .apply(getRequestOptions(DisplayOptions.build().setPlaceholder(placeholder)))
                .into(target);
    }

    /**
     * 图片加载方法回调加载
     */
    public static <T> void displayImage(ImageView imageView, T modelLoader, DisplayOptions options, BitmapImageViewTarget target) {
        Glide.with(imageView)
                .asBitmap()
                .load(modelLoader)
                .transition(BitmapTransitionOptions.withCrossFade(crossFade()))
                .apply(getRequestOptions(options))
                .into(target);
    }

    /**
     * 图片加载方法回调加载
     */
    public static <T> void displayImage(ImageView imageView, T modelLoader, DrawableImageViewTarget target) {
        Glide.with(imageView)
                .load(modelLoader)
                .transition(DrawableTransitionOptions.with(crossFade()))
                .into(target);
    }

    /**
     * 图片加载方法回调加载
     */
    public static <T> void displayImage(ImageView imageView, T modelLoader, int placeholder, DrawableImageViewTarget target) {
        Glide.with(imageView)
                .load(modelLoader)
                .transition(DrawableTransitionOptions.with(crossFade()))
                .apply(getRequestOptions(DisplayOptions.build().setPlaceholder(placeholder)))
                .into(target);
    }


    /**
     * 图片加载方法回调加载
     */
    public static <T> void displayImage(ImageView imageView, T modelLoader, DisplayOptions options, DrawableImageViewTarget target) {
        Glide.with(imageView)
                .load(modelLoader)
                .transition(DrawableTransitionOptions.with(crossFade()))
                .apply(getRequestOptions(options))
                .into(target);
    }

    private static RequestManager getManager(ImageView imageView) {
        try {
            return Glide.with(imageView);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 清除image上的图片
     */
    public static void clearImage(ImageView imageView) {
        Glide.with(imageView).clear(imageView);
    }


    private static DrawableCrossFadeFactory crossFade() {
        return new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
    }

    @SuppressLint("CheckResult")
    private static RequestOptions getRequestOptions(DisplayOptions options) {
        RequestOptions requestOptions = new RequestOptions();
        if (options == null) {
            return requestOptions;
        }
        if (options.getDiskCacheStrategy() != null) {
            requestOptions.diskCacheStrategy(options.getDiskCacheStrategy());
        }
        if (options.getPlaceholder() != DisplayOptions.RES_NONE) {
            requestOptions.placeholder(options.getPlaceholder());
        }
        if (options.getError() != DisplayOptions.RES_NONE) {
            requestOptions.error(options.getError());
        }
        if (options.getTransformation() != null) {
            requestOptions.transform(options.getTransformation());
        }
        if (options.getWidth() != DisplayOptions.RES_NONE && options.getHeight() != DisplayOptions.RES_NONE) {
            requestOptions.override(options.getWidth(), options.getHeight());
        }
        return requestOptions;
    }
}
