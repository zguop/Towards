package com.waitou.photopicker.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import com.waitou.photopicker.Wisdom
import com.waitou.photopicker.bean.Album
import com.waitou.photopicker.bean.ResultMedia
import com.waitou.photopicker.call.ILoaderAlbumCall
import com.waitou.photopicker.call.ILoaderMediaCall
import com.waitou.photopicker.call.OnResultMediaListener
import com.waitou.photopicker.config.WisdomConfig
import com.waitou.photopicker.loader.AlbumCollection
import com.waitou.photopicker.loader.MediaCollection
import com.waitou.photopicker.utils.CameraStrategy

/**
 * auth aboom
 * date 2019-05-24
 */
abstract class PhotoWallFragment : Fragment(), ILoaderAlbumCall, ILoaderMediaCall {

    var onResultMediaListener: OnResultMediaListener? = null
    private val albumCollection by lazy { AlbumCollection() }
    private val mediaCollection by lazy { MediaCollection() }
    private val cameraStrategy by lazy { CameraStrategy(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            albumCollection.onCreate(it, this)
            mediaCollection.onCreate(it, this)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkPermissionOnStart()
    }

    private fun checkPermissionOnStart() {
        activity?.let {
            if (ActivityCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
            } else {
                startLoading()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (permissions.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    startLoading()
                }
                if (permissions.contains(Manifest.permission.CAMERA)) {
                    takeMedia()
                }
            } else {
                if (shouldShowRequestPermissionRationale(permissions[0])) {
                    checkPermissionOnDenied(emptyArray(), permissions)
                } else {
                    checkPermissionOnDenied(permissions, permissions)
                }
            }
        }
    }

    fun loadAlbum() {
        albumCollection.loadAlbum()
    }

    fun loadMedia(bucketId: String = Album.ALBUM_ID_ALL) {
        mediaCollection.loadMedia(bucketId)
    }

    fun takeMedia() {
        activity?.let {
            if (ActivityCompat.checkSelfPermission(it, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
            } else {
                cameraStrategy.startCamera(it, WisdomConfig.getInstance().authorities, WisdomConfig.getInstance().directory)
            }
        }
    }

    fun finish(resultMedias: ArrayList<ResultMedia>) {
        val i = Intent()
        i.putParcelableArrayListExtra(Wisdom.EXTRA_RESULT_SELECTION, resultMedias)
        onResultMediaListener?.onResultFinish(i)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK != resultCode) {
            return
        }
        // the camera callback
        if (CameraStrategy.CAMERA_REQUEST == requestCode) {
            onCameraResult(cameraStrategy.onCameraResult())
        }
    }

    abstract fun startLoading()
    open fun checkPermissionOnDenied(permissionsDeniedForever: Array<String>, permissionsDenied: Array<String>) {}
    open fun onCameraResult(resultMedia: ResultMedia) {}
}
