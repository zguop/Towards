package com.waitou.photopicker.call

import android.net.Uri
import android.widget.ImageView

/**
 * auth aboom
 * date 2019-06-03
 */
interface IImageEngine {
    fun displayThumbnail(target: ImageView, uri: Uri, resize: Int)
}
