package com.waitou.basic_lib.util;

import android.net.Uri;
import android.widget.ImageView;

import com.waitou.imgloader_lib.DisplayOptions;
import com.waitou.imgloader_lib.ImageLoader;
import com.waitou.wisdom_lib.call.ImageEngine;

import org.jetbrains.annotations.NotNull;

/**
 * auth aboom
 * date 2019-06-17
 */
public class GlideEngine implements ImageEngine {
    @Override
    public void displayAlbum(@NotNull ImageView imageView, @NotNull Uri uri, int w, int h, boolean isGif) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        displayPreviewImage(imageView, uri, w, h, isGif);
    }

    @Override
    public void displayPreviewImage(@NotNull ImageView imageView, @NotNull Uri uri, int w, int h, boolean isGif) {
        ImageLoader.displayImage(imageView, uri, DisplayOptions.build().override(w, h));
    }

    @Override
    public void displayThumbnail(@NotNull ImageView imageView, @NotNull Uri uri, int w, int h, boolean isGif) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        displayPreviewImage(imageView, uri, w, h, isGif);
    }
}
