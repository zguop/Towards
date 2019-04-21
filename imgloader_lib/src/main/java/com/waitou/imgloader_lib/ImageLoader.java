package com.waitou.imgloader_lib;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

/**
 * auth aboom
 * date 2018/8/8
 *  圆角变换 RoundedCorners
 *  圆形变换 circleCrop
 */
public class ImageLoader {

    /**
     * 通用的加载图片的方法
     *
     * @param imageView 加载的view
     * @param url       加载的url
     */
    public static void displayImage(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

    /**
     * 通用的加载图片的方法
     *
     * @param imageView   加载的view
     * @param url         加载的url
     * @param placeholder 占位图
     */
    public static void displayImage(ImageView imageView, String url, int placeholder) {
        Glide.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(getRequestOptions(DisplayOptions.build().setPlaceholder(placeholder)))
                .into(imageView);
    }

    /**
     * 通用的加载图片的方法
     *
     * @param imageView 加载的view
     * @param url       加载的url
     * @param options   配置属性
     */
    public static void displayImage(ImageView imageView, String url, DisplayOptions options) {
        Glide.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(getRequestOptions(options))
                .into(imageView);
    }

    /**
     * 图片加载方法回调加载
     */
    public static void displayImage(ImageView imageView, String url, BitmapImageViewTarget target) {
        Glide.with(imageView)
                .asBitmap()
                .load(url)
                .transition(new BitmapTransitionOptions().crossFade())
                .into(target);
    }

    /**
     * 图片加载方法回调加载
     */
    public static void displayImage(ImageView imageView, String url, int placeholder, BitmapImageViewTarget target) {
        Glide.with(imageView)
                .asBitmap()
                .load(url)
                .transition(new BitmapTransitionOptions().crossFade())
                .apply(getRequestOptions(DisplayOptions.build().setPlaceholder(placeholder)))
                .into(target);
    }

    /**
     * 图片加载方法回调加载
     */
    public static void displayImage(ImageView imageView, String url, DisplayOptions options, BitmapImageViewTarget target) {
        Glide.with(imageView)
                .asBitmap()
                .load(url)
                .transition(new BitmapTransitionOptions().crossFade())
                .apply(getRequestOptions(options))
                .into(target);
    }

    /**
     * 图片加载方法回调加载
     */
    public static void displayImage(ImageView imageView, String url, DrawableImageViewTarget target) {
        Glide.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(target);
    }

    /**
     * 图片加载方法回调加载
     */
    public static void displayImage(ImageView imageView, String url, int placeholder, DrawableImageViewTarget target) {
        Glide.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(getRequestOptions(DisplayOptions.build().setPlaceholder(placeholder)))
                .into(target);
    }


    /**
     * 图片加载方法回调加载
     */
    public static void displayImage(ImageView imageView, String url, DisplayOptions options, DrawableImageViewTarget target) {
        Glide.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(getRequestOptions(options))
                .into(target);
    }

    /**
     * 清除image上的图片
     */
    public static void clearImage(ImageView imageView) {
        Glide.with(imageView).clear(imageView);
    }

    @SuppressLint("CheckResult")
    static RequestOptions getRequestOptions(DisplayOptions options) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (options == null) {
            return requestOptions;
        }
        if (options.getPlaceholder() != DisplayOptions.RES_NONE) {
            requestOptions.placeholder(options.getPlaceholder());
        }
        if (options.getPlaceholder() != DisplayOptions.RES_NONE) {
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
