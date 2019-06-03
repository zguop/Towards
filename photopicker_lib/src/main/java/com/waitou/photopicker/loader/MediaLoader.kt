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
import com.waitou.photopicker.config.WisdomConfig
import com.waitou.photopicker.config.isImages
import com.waitou.photopicker.config.isVideos

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
        if (!WisdomConfig.getInstance().isCamera || !context.applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return cursor
        }
        //添加一个相册的item
        val mc = MatrixCursor(PROJECTION)
        mc.addRow(arrayOf(Media.ITEM_ID_CAPTURE, "capture", "", "", 0, 0))
        return MergeCursor(arrayOf(mc, cursor))
    }

    companion object {
        /**
         * 查询media的字段
         */
      private  val PROJECTION = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.SIZE,
                MediaStore.Audio.Media.DURATION
        )

        /**
         * 查询的条件type
         */
        private val SELECTION_ALL_ARGS = arrayOf(
                MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
                MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        )

        fun newInstance(context: Context, albumId: String?, mimeType: Int): MediaLoader {
            val selectionArgs = mutableListOf<String>()

            var selection = if (isImages(mimeType) || isVideos(mimeType)) {
                "${MediaStore.Files.FileColumns.MEDIA_TYPE}=?"
            } else {
                "(${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?)"
            }

            selection += " AND ${MediaStore.MediaColumns.SIZE}>0"

            when {
                isImages(mimeType) -> selectionArgs.add(SELECTION_ALL_ARGS[0])
                isVideos(mimeType) -> selectionArgs.add(SELECTION_ALL_ARGS[1])
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
