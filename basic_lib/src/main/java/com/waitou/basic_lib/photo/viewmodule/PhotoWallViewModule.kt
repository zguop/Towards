package com.waitou.basic_lib.photo.viewmodule

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.waitou.photopicker.bean.Album

/**
 * auth aboom
 * date 2019-05-26
 */
class PhotoWallViewModule : ViewModel() {

    val albumLiveData = MutableLiveData<List<Album>>()

    val selectCountLiveData = MutableLiveData<Int>()


}
