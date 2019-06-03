package com.waitou.photopicker.config

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import com.waitou.photopicker.Wisdom
import com.waitou.photopicker.call.ICropCall
import com.waitou.photopicker.call.IImageEngine
import com.waitou.photopicker.ui.PhotoWallActivity

/**
 * auth aboom
 * date 2019-05-31
 */
class WisdomBuilder(private val wisdom: Wisdom, mimeType: Int) {
    private val wisdomConfig = WisdomConfig.getResetInstance()

    init {
        wisdomConfig.mimeType = mimeType
    }

    /**
     * open camera default is true
     */
    fun isCamera(isCamera: Boolean): WisdomBuilder {
        wisdomConfig.isCamera = isCamera
        return this
    }

    /**
     * configuring image storage authorization
     * @param authorities "xxx.fileprovider"
     */
    @JvmOverloads
    fun fileProvider(authorities: String, directory: String? = null): WisdomBuilder {
        wisdomConfig.authorities = authorities
        wisdomConfig.directory = directory
        return this
    }

    fun crop(cropCall: ICropCall): WisdomBuilder {
        wisdomConfig.cropCall = cropCall
        return this
    }

    fun selectLimit(maxSelectLimit: Int): WisdomBuilder {
        if (maxSelectLimit < 1) {
            throw IllegalArgumentException("maxSelectLimit not less than one")
        }
        wisdomConfig.maxSelectLimit = maxSelectLimit
        return this
    }

    /**
     * image loading engine, need to be Implemented
     */
    fun imageEngine(iImageEngine: IImageEngine): WisdomBuilder {
        wisdomConfig.iImageEngine = iImageEngine
        return this
    }

    /**
     * start to select media and wait for result.
     */
    fun forResult(requestCode: Int, clazz: Class<out PhotoWallActivity>) {
        val o = wisdom.sojournReference.get()
        if (o is Fragment) {
            val activity = o.activity
            val intent = Intent(activity, clazz)
            o.startActivityForResult(intent, requestCode)
        }
        if (o is Activity) {
            val intent = Intent(o, clazz)
            o.startActivityForResult(intent, requestCode)
        }
    }
}
