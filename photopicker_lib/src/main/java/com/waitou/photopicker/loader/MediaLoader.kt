package com.waitou.photopicker.loader

import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.MatrixCursor
import android.database.MergeCursor
import android.provider.MediaStore
import android.support.v4.content.CursorLoader
import com.waitou.photopicker.bean.Album
import com.waitou.photopicker.bean.Media

/**
 * auth aboom
 * date 2019-05-26
 */
class MediaLoader private constructor(context: Context, selection: String?, selectionArgs: Array<String>?) :
        CursorLoader(context, MediaStore.Files.getContentUri("external"),
                PROJECTION, selection, selectionArgs, MediaStore.Images.Media.DATE_TAKEN + " DESC") {

    override fun loadInBackground(): Cursor? {
        val cursor = super.loadInBackground()
        //设备不具备相机功能
        if (!context.applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return cursor
        }
        //添加一个相册的item
        val mc = MatrixCursor(PROJECTION)
        mc.addRow(arrayOf(Media.ITEM_ID_CAPTURE, "capture", "", 0, 0))
        return MergeCursor(arrayOf(mc, cursor))
    }

    companion object {
        /**
         * 查询media的字段
         */
        private val PROJECTION = arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Audio.AudioColumns.DURATION
        )

        /**
         * 查询的条件type
         */
        private val SELECTION_ALL_ARGS = arrayOf(
                MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
                MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        )

        fun newInstance(context: Context, albumId: String?): MediaLoader {
            var selection: String
            val selectionArgs = mutableListOf<String>()

            val onlyShowImages = false
            val onlyShowVideos = false

            selection = if (onlyShowImages || onlyShowVideos) {
                "${MediaStore.Files.FileColumns.MEDIA_TYPE}=?"
            } else {
                "(${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?)"
            }

            selection += " AND ${MediaStore.MediaColumns.SIZE}>0"

            when {
                onlyShowImages -> selectionArgs.add(SELECTION_ALL_ARGS[0])
                onlyShowVideos -> selectionArgs.add(SELECTION_ALL_ARGS[1])
                else -> selectionArgs.addAll(SELECTION_ALL_ARGS)
            }

            if (Album.ALBUM_ID_ALL != albumId) {
                selection += " AND ${MediaStore.Images.Media.BUCKET_ID}=?"
                selectionArgs.add(albumId!!)
            }

            return MediaLoader(context, selection, selectionArgs.toTypedArray())
        }
    }
}
