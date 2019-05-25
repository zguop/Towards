package com.waitou.photopicker

import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import java.lang.ref.WeakReference

/**
 * auth aboom
 * date 2019-05-24
 */
class AtlasCollection : LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var context: WeakReference<FragmentActivity>

    fun onCreate(activity: FragmentActivity) {
        this.context = WeakReference(activity)
    }

    fun imageDataSource(){
        context.get()?.let {
//            it.supportLoaderManager.initLoader(null)
        }
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
        return CursorLoader(context.get()!!)
    }

    override fun onLoadFinished(p0: Loader<Cursor>, p1: Cursor?) {
    }

    override fun onLoaderReset(p0: Loader<Cursor>) {
    }

}
