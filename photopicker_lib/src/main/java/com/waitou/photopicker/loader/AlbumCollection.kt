package com.waitou.photopicker.loader

import android.database.Cursor
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.FragmentActivity
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import com.waitou.photopicker.bean.Album
import com.waitou.photopicker.call.ILoaderAlbumCall
import java.lang.ref.WeakReference

/**
 * auth aboom
 * date 2019-05-24
 */
class AlbumCollection : LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        private const val LOADER_ID = 1
    }

    private lateinit var context: WeakReference<FragmentActivity>
    private lateinit var onLoaderCallbacks: WeakReference<ILoaderAlbumCall>
    private val loaderManager by lazy { context.get()?.let { LoaderManager.getInstance(it) } }


    fun onCreate(@NonNull activity: FragmentActivity, @NonNull onLoaderCallbacks: ILoaderAlbumCall) {
        this.context = WeakReference(activity)
        this.onLoaderCallbacks = WeakReference(onLoaderCallbacks)
    }

    fun loadAlbum() {
        loaderManager?.initLoader(LOADER_ID, null, this)
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
        return AlbumLoader.newInstance(context.get()!!)
    }

    override fun onLoadFinished(p0: Loader<Cursor>, cursor: Cursor?) {
        context.get()?.let {
            cursor?.let {
                if (!cursor.isBeforeFirst) {
                    return
                }
                val list = mutableListOf<Album>()
                while (it.moveToNext()) {
                    val photoFolder = Album.valueOf(it)
                    list.add(photoFolder)
                }
                onLoaderCallbacks.get()?.albumResult(list)
                loaderManager?.destroyLoader(LOADER_ID)
            }
        }
    }

    override fun onLoaderReset(p0: Loader<Cursor>) {
    }
}
