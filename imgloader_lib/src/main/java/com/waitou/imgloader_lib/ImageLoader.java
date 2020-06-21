package com.waitou.imgloader_lib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
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
    public static void displayImage(ImageView imageView, Object modelLoader) {
        RequestManager requestManager = parseWithAction(imageView);
        if (requestManager != null)
            requestManager.load(modelLoader)
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
    public static void displayImage(ImageView imageView, Object modelLoader, int placeholder) {
        RequestManager requestManager = parseWithAction(imageView);
        if (requestManager != null)
            requestManager.load(modelLoader)
                    .transition(DrawableTransitionOptions.with(crossFade()))
                    .apply(getRequestOptions(DisplayOptions.build().placeholder(placeholder)))
                    .into(imageView);
    }

    /**
     * 通用的加载图片的方法
     *
     * @param imageView   加载的view
     * @param modelLoader glide load方法支持的所有类型
     * @param options     配置属性
     */
    public static void displayImage(ImageView imageView, Object modelLoader, DisplayOptions options) {
        RequestManager requestManager = parseWithAction(imageView);
        if (requestManager != null) {
            requestManager.load(modelLoader)
                    .transition(DrawableTransitionOptions.with(crossFade()))
                    .apply(getRequestOptions(options))
                    .into(imageView);
        }
    }

    /**
     * 图片加载方法回调加载
     */
    public static void displayImage(Object with, Object modelLoader, BitmapImageViewTarget target) {
        RequestManager requestManager = parseWithAction(with);
        if (requestManager != null) {
            requestManager.asBitmap()
                    .load(modelLoader)
                    .transition(BitmapTransitionOptions.withCrossFade(crossFade()))
                    .into(target);
        }
    }

    /**
     * 图片加载方法回调加载
     */
    public static void displayImage(Object with, Object modelLoader, int placeholder, BitmapImageViewTarget target) {
        RequestManager requestManager = parseWithAction(with);
        if (requestManager != null) {
            requestManager.asBitmap()
                    .load(modelLoader)
                    .transition(BitmapTransitionOptions.withCrossFade(crossFade()))
                    .apply(getRequestOptions(DisplayOptions.build().placeholder(placeholder)))
                    .into(target);
        }
    }

    /**
     * 图片加载方法回调加载
     */
    public static void displayImage(Object with, Object modelLoader, DisplayOptions options, BitmapImageViewTarget target) {
        RequestManager requestManager = parseWithAction(with);
        if (requestManager != null) {
            requestManager.asBitmap()
                    .load(modelLoader)
                    .transition(BitmapTransitionOptions.withCrossFade(crossFade()))
                    .apply(getRequestOptions(options))
                    .into(target);
        }
    }

    /**
     * 图片加载方法回调加载
     */
    public static void displayImage(Object with, Object modelLoader, CustomTarget<Drawable> target) {
        RequestManager requestManager = parseWithAction(with);
        if (requestManager != null) {
            requestManager.load(modelLoader)
                    .transition(DrawableTransitionOptions.with(crossFade()))
                    .into(target);
        }
    }

    /**
     * 图片加载方法回调加载
     */
    public static void displayImage(Object with, Object modelLoader, int placeholder, DrawableImageViewTarget target) {
        RequestManager requestManager = parseWithAction(with);
        if (requestManager != null) {
            requestManager.load(modelLoader)
                    .transition(DrawableTransitionOptions.with(crossFade()))
                    .apply(getRequestOptions(DisplayOptions.build().placeholder(placeholder)))
                    .into(target);
        }
    }

    /**
     * 图片加载方法回调加载
     */
    public static void displayImage(Object with, Object modelLoader, DisplayOptions options, DrawableImageViewTarget target) {
        RequestManager requestManager = parseWithAction(with);
        if (requestManager != null) {
            requestManager.load(modelLoader)
                    .transition(DrawableTransitionOptions.with(crossFade()))
                    .apply(getRequestOptions(options))
                    .into(target);
        }
    }

    /**
     * 清除image上的图片
     */
    public static void clearImage(ImageView imageView) {
        Glide.with(imageView).clear(imageView);
    }

    static DrawableCrossFadeFactory crossFade() {
        return new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
    }

    static RequestManager parseWithAction(Object o) {
        try {
            if (o instanceof View) {
                return Glide.with((View) o);
            }
            if (o instanceof Fragment) {
                return Glide.with((Fragment) o);
            }
            if (o instanceof Activity) {
                return Glide.with((Activity) o);
            }
            if (o instanceof Context) {
                return Glide.with((Context) o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
