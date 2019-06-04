package com.waitou.photopicker.bean

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.waitou.photopicker.config.isGif
import com.waitou.photopicker.config.isImage
import com.waitou.photopicker.config.isVideo

/**
 * auth aboom
 * date 2019-05-26
 */
class Media(
        /**
         * 主键
         */
        val mediaId: String,
        /**
         * 名称
         */
        val mediaName: String,
        /**
         * type
         */
        val mediaType: String,
        /**
         * path
         */
        val path: String,
        /**
         * size
         */
        val size: Long,
        /**
         * video in ms
         */
        val duration: Long) {

    /**
     * path
     */
    val uri: Uri

    init {
        val contentUri = when {
            isImage() -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            isVideo() -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            else -> MediaStore.Files.getContentUri("external")
        }
        this.uri = ContentUris.withAppendedId(contentUri, mediaId.toLong())
    }

    fun isCapture(): Boolean {
        return ITEM_ID_CAPTURE == mediaId
    }

    fun isImage(): Boolean {
        return isImage(mediaType)
    }

    fun isVideo(): Boolean {
        return isVideo(mediaType)
    }

    fun isGif():Boolean{
        return isGif(mediaType)
    }

    override fun toString(): String {
        return "Media(mediaId='$mediaId', mediaName='$mediaName', mediaType='$mediaType', path='$path', size=$size, duration=$duration, uri=$uri)"
    }

    companion object {
        const val ITEM_ID_CAPTURE: String = "-1"

        fun valueOf(cursor: Cursor): Media {
            return Media(
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)),
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE)),
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            )
        }
    }
}
