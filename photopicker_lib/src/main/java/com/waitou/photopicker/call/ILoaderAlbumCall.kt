package com.waitou.photopicker.call

import com.waitou.photopicker.bean.Album

/**
 * auth aboom
 * date 2019-05-26
 */
interface ILoaderAlbumCall {
    fun albumResult(albums: List<Album>)
}

