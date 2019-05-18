package com.waitou.towards.model.main.fragment.home.adapterdelegate

import android.graphics.Bitmap
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseViewHolder
import com.to.aboomy.recycler_lib.AdapterDelegate
import com.to.aboomy.recycler_lib.Displayable
import com.waitou.imgloader_lib.HsRoundImageView
import com.waitou.imgloader_lib.ImageLoader
import com.waitou.towards.R
import com.waitou.towards.bean.HomeDataInfo

/**
 * auth aboom
 * date 2019-05-18
 */
class RoundImageDelegate : AdapterDelegate() {
    override fun layout(): Int {
        return R.layout.item_round_image
    }

    override fun isForViewType(item: Displayable): Boolean {
        return item is HomeDataInfo && item.template == 2
    }

    override fun convert(helper: BaseViewHolder, data: Displayable, position: Int) {
        val info = data as HomeDataInfo
        val imageView = helper.getView<HsRoundImageView>(R.id.img)
        ImageLoader.displayImage(imageView, info.templateJson[0].picUrl, object : BitmapImageViewTarget(imageView) {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                imageView.setImageBitmap(resource)
            }
        })
        helper.setText(R.id.text, info.templateJson[0].description)
    }
}
