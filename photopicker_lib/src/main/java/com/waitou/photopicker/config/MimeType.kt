package com.waitou.photopicker.config

/**
 * auth aboom
 * date 2019-05-31
 */
const val TYPE_IMAGE = 0x01
const val TYPE_VIDEO = 0X02

/**
 * all image type
 */
fun ofAll(): Int {
    return TYPE_IMAGE or TYPE_VIDEO
}

/**
 * load only image
 */
fun ofImage(): Int {
    return TYPE_IMAGE
}

/**
 * load only image
 */
fun ofVideo(): Int {
    return TYPE_VIDEO
}

fun onlyImages(): Boolean {
    return WisdomConfig.getInstance().mimeType == TYPE_IMAGE
}

fun onlyVideos(): Boolean {
    return WisdomConfig.getInstance().mimeType == TYPE_VIDEO
}

fun isImage(mediaType: String): Boolean {
    return mediaType.startsWith("image")
}

fun isVideo(mediaType: String): Boolean {
    return mediaType.startsWith("video")
}

fun isGif(mediaType: String): Boolean {
    return mediaType.equals("image/gif", true)
}

fun getCurrentMimeType(mediaType: String): Int = when {
    isImage(mediaType) || isGif(mediaType) -> ofImage()
    else -> ofVideo()
}

