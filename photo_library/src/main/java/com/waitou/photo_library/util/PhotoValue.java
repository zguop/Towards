package com.waitou.photo_library.util;

/**
 * Created by waitou on 17/4/14.
 */

public class PhotoValue {

    //跳转到预览图片点击图片当期的position
    public static final String EXTRA_SELECTED_PHOTO_POSITION = "selected_photo_position";
    //调转到预览图片 选择图片的集合
    public static final String EXTRA_PHOTO_ITEMS             = "extra_photo_items";
    //preview 传递的图片url
    public static final String EXTRA_URL                     = "url";
    //预览图片页面点击完成回到图片选择是否调用提交
    public static       String EXTRA_IS_COMMIT               = "is_commit";
    //预览图片只是查看图片,并不能进行选择
    public static final String EXTRA_IS_PREVIEW              = "is_preview";
    //裁剪后的图片info
    public static final String EXTRA_CROP_PHOTO              = "extra_crop_photo";


}
