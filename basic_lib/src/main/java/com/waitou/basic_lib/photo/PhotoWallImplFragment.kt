package com.waitou.basic_lib.photo

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.waitou.basic_lib.photo.viewmodule.PhotoWallViewModule
import com.waitou.photopicker.PhotoWallFragment
import com.waitou.photopicker.bean.Album
import com.waitou.photopicker.bean.Media

/**
 * auth aboom
 * date 2019-05-25
 */
class PhotoWallImplFragment : PhotoWallFragment() {

    private lateinit var viewModule: PhotoWallViewModule

    override fun albumResult(albums: List<Album>) {
        viewModule.albumLiveData.postValue(albums)
        albums.forEach {
            Log.e("aa", "----- " + it.toString())
        }
    }

    override fun mediaResult(medias: List<Media>) {

    }

    override fun startLoading() {
        loadAlbum()
        loadMedia()
    }

    companion object {
        val TAG = PhotoWallImplFragment::class.java.simpleName
        fun newInstance(): PhotoWallFragment {
            return PhotoWallImplFragment()
        }
    }

    override fun checkPermissionOnDenied(permissionsDeniedForever: Array<String>, permissionsDenied: Array<String>) {
        super.checkPermissionOnDenied(permissionsDeniedForever, permissionsDenied)
        val msg = if (permissionsDeniedForever.isEmpty())
            "需要访问你的存储设备来选择图片" else "需要访问你的存储设备来选择图片，请在“系统设置”或授权对话框中允许“存储空间”权限。"
        ToastUtils.showShort(msg)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val textView = TextView(activity)
        textView.setTextColor(Color.BLACK)
        textView.text = javaClass.simpleName
        return textView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModule = ViewModelProviders.of(activity!!)[PhotoWallViewModule::class.java]

    }
}
