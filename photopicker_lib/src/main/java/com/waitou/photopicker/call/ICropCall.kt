package com.waitou.photopicker.call

import android.content.Intent
import java.io.File

/**
 * auth aboom
 * date 2019-06-01
 */
interface ICropCall {
    fun onStartCrop(sojourn: Any, path: File, requestCode: Int)
    fun onCropFinish(resultCode: Int, data: Intent)
}
