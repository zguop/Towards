package com.waitou.photopicker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.View
import com.waitou.photopicker.bean.Album
import com.waitou.photopicker.call.ILoaderAlbumCall
import com.waitou.photopicker.call.ILoaderMediaCall
import com.waitou.photopicker.loader.AlbumCollection
import com.waitou.photopicker.loader.MediaCollection

/**
 * auth aboom
 * date 2019-05-24
 */
abstract class PhotoWallFragment : Fragment(), ILoaderAlbumCall, ILoaderMediaCall {

    private val albumCollection by lazy { AlbumCollection() }
    private val mediaCollection by lazy { MediaCollection() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            albumCollection.onCreate(it, this)
            mediaCollection.onCreate(it, this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                startLoading()
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

    override fun onDestroy() {
        super.onDestroy()
        albumCollection.onDestroy()
        mediaCollection.onDestroy()
    }

    abstract fun startLoading()
    open fun checkPermissionOnDenied(permissionsDeniedForever: Array<String>, permissionsDenied: Array<String>) {}
}
