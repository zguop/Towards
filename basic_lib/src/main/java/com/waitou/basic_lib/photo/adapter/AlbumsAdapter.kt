package com.waitou.basic_lib.photo.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.waitou.basic_lib.R
import com.waitou.imgloader_lib.ImageLoader
import com.waitou.photopicker.bean.Album
import kotlinx.android.synthetic.main.bs_item_albums.view.*

/**
 * auth aboom
 * date 2019-05-25
 */
class AlbumsAdapter : BaseQuickAdapter<Album, BaseViewHolder>(R.layout.bs_item_albums) {

    var currentAlbumPos: Int = 0

    override fun convert(helper: BaseViewHolder, item: Album) {
        ImageLoader.displayImage(helper.itemView.img, item.albumPath)
        helper.itemView.name.text = item.albumName
        helper.itemView.num.text = item.count.toString()
        helper.itemView.check.visibility = if(helper.adapterPosition == currentAlbumPos) View.VISIBLE else View.GONE
    }
}
