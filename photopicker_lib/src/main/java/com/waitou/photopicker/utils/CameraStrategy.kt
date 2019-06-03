package com.waitou.photopicker.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import com.waitou.photopicker.bean.ResultMedia
import java.io.File
import java.io.IOException
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

/**
 * auth aboom
 * date 2019-05-29
 */


class CameraStrategy(fragment: Fragment) {

    private val weakReference = WeakReference<Fragment>(fragment)

    private var filePath: File? = null
    private var fileUri: Uri? = null
    fun startCamera(context: Context, authority: String, directory: String?) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(context.packageManager)?.let {
            filePath = checkImageFileExistsAndCreate(directory) ?: return@let
            fileUri = FileProvider.getUriForFile(context, authority, filePath!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            weakReference.get()?.startActivityForResult(intent, CAMERA_REQUEST)
        }
    }

    private fun checkImageFileExistsAndCreate(directory: String?): File? {
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            try {
                //the path of /storage/emulated/0/Pictures
                var storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                if (!directory.isNullOrEmpty()) {
                    storageDir = File(storageDir, directory)
                }
                if (!storageDir.exists()) {
                    storageDir.mkdirs()
                }
                val fileName = String.format("IMAGE_%s.jpg",
                        SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
                                .format(Date()))
                return File(storageDir, fileName)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun onCameraResult(): ResultMedia {
        filePath?.let {
            weakReference.get()?.activity?.application?.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(it)))
        }
        val resultMedia = ResultMedia()
        resultMedia.mediaPath = filePath?.absolutePath
        resultMedia.mediaUri = fileUri
        return resultMedia
    }

    companion object {
        const val CAMERA_REQUEST = 0X11
    }
}




