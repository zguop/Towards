package com.waitou.imgloader_lib;

import android.graphics.Bitmap;

import androidx.annotation.IdRes;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * auth aboom
 * date 2018/8/8
 */
public class DisplayOptions {

    static final int RES_NONE = -1;

    /*--------------- 全局配置 ---------------*/
    public static int RES_PLACE_HOLDER = RES_NONE;
    public static int RES_ERROR        = RES_NONE;
    /*--------------- end ---------------*/

    /**
     * 占位图
     */
    @IdRes
    private int placeholder = RES_PLACE_HOLDER;
    /**
     * 加载失败图片
     */
    @IdRes
    private int error       = RES_ERROR;

    /**
     * 加载图片指定宽高
     */
    private int width;
    private int height;

    /**
     * 自定义的transformation
     * 圆角变换 RoundedCorners
     * 圆形变换 circleCrop
     */
    private Transformation<Bitmap> transformation;

    private DiskCacheStrategy diskCacheStrategy;

    private DisplayOptions() {
    }

    public static DisplayOptions build() {
        return new DisplayOptions();
    }

    public DisplayOptions placeholder(int placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public DisplayOptions error(int error) {
        this.error = error;
        return this;
    }

    public DisplayOptions transformation(Transformation<Bitmap> transformation) {
        this.transformation = transformation;
        return this;
    }

    public DisplayOptions override(int width,int height){
        this.width = width;
        this.height = height;
        return this;
    }

    public DisplayOptions diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
        this.diskCacheStrategy = diskCacheStrategy;
        return this;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getError() {
        return error;
    }

    public Transformation<Bitmap> getTransformation() {
        return transformation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public DiskCacheStrategy getDiskCacheStrategy() {
        return diskCacheStrategy;
    }
}
