package com.waitou.photopicker.utils

import android.content.res.Configuration
import android.content.res.Resources
import com.waitou.photopicker.config.WisdomConfig

/**
 * auth aboom
 * date 2019-06-03
 */
fun isSingleImage(): Boolean {
    return WisdomConfig.getInstance().maxSelectLimit == 1
}


/**
 * get the right image size
 */
fun getScreenImageResize(): Int {
    return when (Resources.getSystem().configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
        Configuration.SCREENLAYOUT_SIZE_SMALL -> 100
        Configuration.SCREENLAYOUT_SIZE_NORMAL -> 180
        Configuration.SCREENLAYOUT_SIZE_LARGE -> 320
        else -> 180
    }
}