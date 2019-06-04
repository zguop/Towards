package com.waitou.imgloader_lib;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 * auth aboom
 * date 2019/4/21
 */
public class ImageLoaderBinding {

    @BindingAdapter(value = {"imageURI", "placeholderImage", "failureImage"}, requireAll = false)
    public static void displayImage(ImageView imageView, String url, Drawable placeholderImage, Drawable failureImage) {
        Glide.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(new RequestOptions().placeholder(placeholderImage).error(failureImage))
                .into(imageView);
    }

}
