package com.waitou.photopicker.utils

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import java.text.SimpleDateFormat
import java.util.*

/**
 * auth aboom
 * date 2019-05-29
 */
internal fun startCamera(context: Context) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    intent.resolveActivity(context.packageManager)?.let {

        val fileName = String.format("IMAGE_%s.jpg",
                SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
                        .format(Date()))
        //the path of /storage/emulated/0/Pictures
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        if (!storageDir.exists()) storageDir.mkdirs()






    }
}