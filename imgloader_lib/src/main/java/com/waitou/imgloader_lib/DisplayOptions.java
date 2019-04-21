package com.waitou.imgloader_lib;

import android.graphics.Bitmap;
import android.support.annotation.IdRes;

import com.bumptech.glide.load.Transformation;

/**
 * auth aboom
 * date 2018/8/8
 */
public class DisplayOptions {

    static final int RES_NONE = -1;

    /*--------------- 全局配置 ---------------*/
    public static final int RES_PLACE_HOLDER = RES_NONE;
    public static final int RES_ERROR        = RES_NONE;
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
     */
    private Transformation<Bitmap> transformation;


    private DisplayOptions() {
    }

    public static DisplayOptions build() {
        return new DisplayOptions();
    }

    public DisplayOptions setPlaceholder(int placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public DisplayOptions setError(int error) {
        this.error = error;
        return this;
    }

    public DisplayOptions setTransformation(Transformation<Bitmap> transformation) {
        this.transformation = transformation;
        return this;
    }

    public DisplayOptions setWidth(int width) {
        this.width = width;
        return this;
    }

    public DisplayOptions setHeight(int height) {
        this.height = height;
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
}
