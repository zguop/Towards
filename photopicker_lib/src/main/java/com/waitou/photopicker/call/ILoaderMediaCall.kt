package com.waitou.photopicker.call

import com.waitou.photopicker.bean.Media

/**
 * auth aboom
 * date 2019-05-26
 */
interface ILoaderMediaCall {
    fun mediaResult(medias: List<Media>)
}
