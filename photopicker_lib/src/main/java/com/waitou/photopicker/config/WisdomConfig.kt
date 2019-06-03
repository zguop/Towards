package com.waitou.photopicker.config

import com.waitou.photopicker.call.ICropCall
import com.waitou.photopicker.call.IImageEngine

/**
 * auth aboom
 * date 2019-05-30
 */
class WisdomConfig private constructor() {

    var mimeType = ofAll()
    var isCamera = true
    var authorities = ""
    var directory: String? = null
    var cropCall: ICropCall? = null
    var maxSelectLimit = 1
    var iImageEngine: IImageEngine? = null

    fun reset() {
        mimeType = ofAll()
        isCamera = true
        authorities = ""
        directory = null
        cropCall = null
        maxSelectLimit = 1
        iImageEngine = null
    }

    companion object {
        fun getInstance(): WisdomConfig {
            return InstanceHolder.INSTANCE
        }

        internal fun getResetInstance(): WisdomConfig {
            val instance = getInstance()
            instance.reset()
            return instance
        }
    }

    private object InstanceHolder {
        val INSTANCE = WisdomConfig()
    }
}
