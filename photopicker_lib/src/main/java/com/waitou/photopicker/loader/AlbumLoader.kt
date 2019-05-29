package com.waitou.photopicker.loader

import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.database.MergeCursor
import android.provider.MediaStore
import android.support.v4.content.CursorLoader
import com.waitou.photopicker.bean.Album

/**
 * auth aboom
 * date 2019-05-25
 */
class AlbumLoader private constructor(context: Context, selection: String, selectionArgs: Array<String>) :
        CursorLoader(context, MediaStore.Files.getContentUri("external"),
                PROJECTION, selection, selectionArgs, MediaStore.Images.Media.DATE_TAKEN + " DESC") {

    override fun loadInBackground(): Cursor? {
        val cursor = super.loadInBackground()
        //创建一张虚拟表，表字段包含 COLUMNS
        val allAlbum = MatrixCursor(COLUMNS)
        //得到 文件夹下的图片总数
        var totalCount = 0
        //拿第一张图片当做封面图片
        var allAlbumCoverPath = ""
        if (cursor != null) {
            while (cursor.moveToNext()) {
                totalCount += cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT))
            }
            if (cursor.moveToFirst()) {
                allAlbumCoverPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
            }
        }
        //插入一条记录到虚拟表
        allAlbum.addRow(arrayOf(Album.ALBUM_ID_ALL, Album.ALBUM_ID_ALL, Album.ALBUM_NAME_ALL, allAlbumCoverPath, totalCount.toString()))
        //合并结果集
        return MergeCursor(arrayOf(allAlbum, cursor))
    }

    companion object {
        const val COLUMN_COUNT = "count"

        /**
         * 查询表的字段
         */
        private val PROJECTION = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID, //相册id
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, //相册名称
                MediaStore.Images.Media.DATA,
                "COUNT(*) AS $COLUMN_COUNT")

        /**
         * 虚拟表字段结构
         */
        private val COLUMNS = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                COLUMN_COUNT)

        /**
         * 查询的类型
         */
        private val SELECTION_ARGS = arrayOf(
                MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
                MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString())

        fun newInstance(context: Context): CursorLoader {
            val selectionArgs = mutableListOf<String>()

            val onlyShowImages = false
            val onlyShowVideos = false

            /**
             * SELECT _id, bucket_id, bucket_display_name, _data, COUNT(*) AS count FROM files WHERE ((media_type=? OR media_type=?) AND _size>0) GROUP BY（bucket_id) ORDER BY datetaken DESC
             */
            val selection = if (onlyShowImages || onlyShowVideos) {
                "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? AND ${MediaStore.MediaColumns.SIZE}>0) GROUP BY (${MediaStore.Images.Media.BUCKET_ID}"
            } else {
                "(${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?) AND ${MediaStore.MediaColumns.SIZE}>0) GROUP BY (${MediaStore.Images.Media.BUCKET_ID}"
            }
            when {
                onlyShowImages -> selectionArgs.add(SELECTION_ARGS[0])
                onlyShowVideos -> selectionArgs.add(SELECTION_ARGS[1])
                else -> selectionArgs.addAll(SELECTION_ARGS)
            }
            return AlbumLoader(context, selection, selectionArgs.toTypedArray())
        }
    }

}


