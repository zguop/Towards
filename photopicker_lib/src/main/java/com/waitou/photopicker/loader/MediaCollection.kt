package com.waitou.photopicker.loader

import android.database.Cursor
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.FragmentActivity
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import com.waitou.photopicker.bean.Media
import com.waitou.photopicker.call.ILoaderMediaCall
import com.waitou.photopicker.config.WisdomConfig
import java.lang.ref.WeakReference

/**
 * auth aboom
 * date 2019-05-26
 */
class MediaCollection : LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        private const val ARGS_ALBUM_ID = "album_id"
    }

    private lateinit var context: WeakReference<FragmentActivity>
    private lateinit var iLoaderMediaCall: WeakReference<ILoaderMediaCall>
    private val loaderManager by lazy { context.get()?.let { LoaderManager.getInstance(it) } }


    fun onCreate(@NonNull activity: FragmentActivity, @NonNull iLoaderMediaCall: ILoaderMediaCall) {
        this.context = WeakReference(activity)
        this.iLoaderMediaCall = WeakReference(iLoaderMediaCall)
    }

    fun loadMedia(albumId: String) {
        val bundle = Bundle()
        bundle.putString(ARGS_ALBUM_ID, albumId)
        loaderManager?.initLoader(WisdomConfig.getInstance().mimeType, bundle, this)
    }

    override fun onCreateLoader(id: Int, bundle: Bundle?): Loader<Cursor> {
        val albumId = bundle?.getString(ARGS_ALBUM_ID)
        return MediaLoader.newInstance(context.get()!!, albumId, id)
    }

    override fun onLoadFinished(p0: Loader<Cursor>, cursor: Cursor?) {
        context.get()?.let {
            cursor?.let {
                if (!cursor.isBeforeFirst) {
                    return
                }
                val list = mutableListOf<Media>()
                while (it.moveToNext()) {
                    val media = Media.valueOf(cursor)
                    list.add(media)
                }
                iLoaderMediaCall.get()?.mediaResult(list)
                loaderManager?.destroyLoader(WisdomConfig.getInstance().mimeType)
            }
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }
}
