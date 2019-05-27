package com.waitou.photopicker.bean

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

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
        val displayName: String,
        /**
         * type
         */
        val mediaType: String,
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

    fun isImage(): Boolean {
        return mediaType.startsWith("image")
    }

    fun isVideo(): Boolean {
        return mediaType.startsWith("video")
    }

    override fun toString(): String {
        return "Media(mediaId='$mediaId', displayName='$displayName', mediaType='$mediaType', size=$size, duration=$duration, uri=$uri)"
    }

    companion object {
        fun valueOf(cursor: Cursor): Media {
            return Media(
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)),
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE)),
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            )
        }
    }
}
