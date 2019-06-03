package com.waitou.towards.model.activity;

import android.net.Uri;
import android.widget.ImageView;

import com.waitou.imgloader_lib.DisplayOptions;
import com.waitou.imgloader_lib.ImageLoader;
import com.waitou.photopicker.call.IImageEngine;

import org.jetbrains.annotations.NotNull;

/**
 * auth aboom
 * date 2019-06-03
 */
public class GlideEngine implements IImageEngine {

    @Override
    public void displayThumbnail(@NotNull ImageView target, @NotNull Uri uri, int resize) {
        ImageLoader.displayImage(target, uri, DisplayOptions.build().setHeight(resize).setWidth(resize));
    }
}
