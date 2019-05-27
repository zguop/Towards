package com.waitou.photopicker.bean

import android.database.Cursor
import android.provider.MediaStore
import com.waitou.photopicker.loader.AlbumLoader

/**
 * auth aboom
 * date 2019-05-25
 */
class Album(
        /**
         * 主键
         */
        val id: String,
        /**
         * 相册id
         */
        val albumId: String,
        /**
         * 相册名称
         */
        val albumName: String,
        /**
         * 相册图片路径
         */
        val albumPath: String,
        /**
         * 相册有多少张图
         */
        val count: Int) {

    override fun toString(): String {
        return "Album(mediaId='$id', albumId='$albumId', albumName='$albumName', albumPath='$albumPath', count=$count)"
    }

    companion object {
        const val ALBUM_ID_ALL = "-1"
        const val ALBUM_NAME_ALL = "All"

        fun valueOf(cursor: Cursor): Album {
            return Album(
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(AlbumLoader.COLUMN_COUNT))
            )
        }
    }
}
