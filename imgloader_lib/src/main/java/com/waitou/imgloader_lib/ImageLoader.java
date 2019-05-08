package com.waitou.imgloader_lib;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

/**
 * auth aboom
 * date 2018/8/8
 * 圆角变换 RoundedCorners
 * 圆形变换 circleCrop
 * <p>
 * <p>
 * CenterCrop,FitCenter
 * CenterCrop,FitCenter都是对目标图片进行裁剪,了解过ImageView的ScaleType属性就知道,
 * 这2种裁剪方式在ImageView上也是有的,分别对应ImageView的ImageView.ScaleType.CENTER_CROP和mageView.ScaleType.FIT_CENTER的.
 * <p>
 * DiskCacheStrategy.NONE:什么都不缓存
 * DiskCacheStrategy.SOURCE:仅缓存原图(全分辨率的图片)
 * DiskCacheStrategy.RESULT:仅缓存最终的图片,即修改了尺寸或者转换后的图片
 * DiskCacheStrategy.ALL:缓存所有版本的图片,默认模式
 * <p>
 * 下载优先级
 * • Priority.LOW
 * • Priority.NORMAL
 * • Priority.HIGH
 * • Priority.IMMEDIATE
 * <p>
 * Glide.with(context)
 * .load(url)
 * .asBitmap() 强制处理成bitmap
 * .asGif() 指明了加载gif图，即使不指定Glide也会自己判断
 * .placeholder() 加载中显示的图片
 * .error()加载错误显示的图片
 * .crossFade()淡入显示，如果设置了这个则需要去掉asBitmap
 * .dontAnimte()直接显示图片
 * .override(80,80).//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
 * .centerCrop().//中心裁剪,缩放填充至整个ImageView
 * .skipMemoryCache(true).//跳过内存缓存
 * .diskCacheStrategy(DiskCacheStrategy.RESULT).//保存最终图片
 * .thumbnail(0.1f).//10%的原图大小
 * .transform(new BlurTransformation(this)).//高斯模糊处理
 * .animate(R.anim.left_in).//加载xml文件定义的动画
 * <p>
 * 清理磁盘缓存 需要在子线程中执行
 * Glide.get(this).clearDiskCache();
 * 清理内存缓存  可以在UI主线程中进
 * Glide.get(this).clearMemory();
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
                .transition(new DrawableTransitionOptions().crossFade())
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
                .transition(new DrawableTransitionOptions().crossFade())
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
                .transition(new DrawableTransitionOptions().crossFade())
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
                .transition(new BitmapTransitionOptions().crossFade())
                .into(target);
    }

    /**
     * 图片加载方法回调加载
     */
    public static <T> void displayImage(ImageView imageView, T modelLoader, int placeholder, BitmapImageViewTarget target) {
        Glide.with(imageView)
                .asBitmap()
                .load(modelLoader)
                .transition(new BitmapTransitionOptions().crossFade())
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
                .transition(new BitmapTransitionOptions().crossFade())
                .apply(getRequestOptions(options))
                .into(target);
    }

    /**
     * 图片加载方法回调加载
     */
    public static <T> void displayImage(ImageView imageView, T modelLoader, DrawableImageViewTarget target) {
        Glide.with(imageView)
                .load(modelLoader)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(target);
    }

    /**
     * 图片加载方法回调加载
     */
    public static <T> void displayImage(ImageView imageView, T modelLoader, int placeholder, DrawableImageViewTarget target) {
        Glide.with(imageView)
                .load(modelLoader)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(getRequestOptions(DisplayOptions.build().setPlaceholder(placeholder)))
                .into(target);
    }


    /**
     * 图片加载方法回调加载
     */
    public static <T> void displayImage(ImageView imageView, T modelLoader, DisplayOptions options, DrawableImageViewTarget target) {
        Glide.with(imageView)
                .load(modelLoader)
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
        if (options == null) {
            return requestOptions;
        }
        if (options.getDiskCacheStrategy() != null) {
            requestOptions.diskCacheStrategy(options.getDiskCacheStrategy());
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
