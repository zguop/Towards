package com.waitou.basic_lib.photo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.Gravity
import com.to.aboomy.statusbar_lib.StatusBarUtil
import com.waitou.basic_lib.R
import com.waitou.basic_lib.photo.adapter.AlbumsAdapter
import com.waitou.basic_lib.photo.view.FolderPopUpWindow
import com.waitou.basic_lib.photo.viewmodule.PhotoWallViewModule
import com.waitou.photopicker.bean.Album
import com.waitou.photopicker.ui.PhotoWallActivity
import com.waitou.photopicker.ui.PhotoWallFragment
import kotlinx.android.synthetic.main.bs_activity_photo_wall_impl.*

/**
 * auth aboom
 * date 2019-05-25
 */
class PhotoWallImplActivity : PhotoWallActivity() {

    private val albumsAdapter by lazy { AlbumsAdapter() }
    private var popUpWindow: FolderPopUpWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bs_activity_photo_wall_impl)
        StatusBarUtil.immersiveStatusBarNeedDark(this)
        val viewModule = ViewModelProviders.of(this)[PhotoWallViewModule::class.java]
        viewModule.albumLiveData.observe(this, Observer { addAlbum(it) })
        folder.setOnClickListener { showPop() }
    }

    override fun onCreateBoxingView(): PhotoWallFragment {
        var fragment = supportFragmentManager.findFragmentByTag(PhotoWallImplFragment.TAG)
        if (fragment !is PhotoWallFragment) {
            fragment = PhotoWallImplFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.contentLayout, fragment, PhotoWallImplFragment.TAG).commitAllowingStateLoss()
        }
        return fragment
    }

    private fun addAlbum(data: List<Album>?) {
        data?.let {
            albumsAdapter.replaceData(it)
            folder.text = it[0].albumName
            preview.text = getString(R.string.bs_preview_count, "0")
        }
    }

    private fun showPop() {
        if (popUpWindow == null) {
            popUpWindow = FolderPopUpWindow(this, albumsAdapter)
            albumsAdapter.setOnItemClickListener { _, _, position ->
                if (albumsAdapter.currentAlbumPos != position) {
                    albumsAdapter.currentAlbumPos = position
                    val album = albumsAdapter.data[position]
                    albumsAdapter.notifyDataSetChanged()
                    folder.text = album?.albumName
                    val fragment = supportFragmentManager.findFragmentByTag(PhotoWallImplFragment.TAG)
                    if (fragment is PhotoWallFragment) {
                        fragment.loadMedia(album.albumId)
                    }
                }
                popUpWindow?.dismiss()
            }
        }
        popUpWindow?.let {
            if (it.isShowing) {
                it.dismiss()
            }
            it.showAtLocation(footer, Gravity.BOTTOM, 0, 400)
        }
    }

}
