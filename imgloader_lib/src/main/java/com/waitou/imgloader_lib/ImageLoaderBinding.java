package com.waitou.imgloader_lib;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 * auth aboom
 * date 2019/4/21
 */
public class ImageLoaderBinding {

    @BindingAdapter(value = {"imageURI", "placeholderImage", "failureImage"}, requireAll = false)
    public static void displayImage(ImageView imageView, String url, Drawable placeholderImage, Drawable failureImage) {
        RequestManager requestManager = ImageLoader.parseWithAction(imageView);
        if (requestManager != null)
            requestManager.load(url)
                    .transition(DrawableTransitionOptions.with(ImageLoader.crossFade()))
                    .apply(new RequestOptions().placeholder(placeholderImage).error(failureImage))
                    .into(imageView);
    }
}
