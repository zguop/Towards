package com.waitou.photopicker.config

/**
 * auth aboom
 * date 2019-05-31
 */
const val TYPE_IMAGE = 0x01
const val TYPE_VIDEO = 0X02

fun ofAll(): Int {
    return TYPE_IMAGE or TYPE_VIDEO
}

fun ofImage(): Int {
    return TYPE_IMAGE
}

fun ofVideo(): Int {
    return TYPE_VIDEO
}

fun isImages(mimeType: Int): Boolean {
    return mimeType == TYPE_IMAGE
}

fun isVideos(mimeType: Int): Boolean {
    return mimeType == TYPE_VIDEO
}
