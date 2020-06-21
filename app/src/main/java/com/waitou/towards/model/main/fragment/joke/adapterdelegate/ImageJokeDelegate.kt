package com.waitou.towards.model.main.fragment.joke.adapterdelegate

import android.text.TextUtils
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.to.aboomy.recycler_lib.adapter.Displayable
import com.to.aboomy.recycler_lib.delegate.AdapterDelegate
import com.waitou.basic_lib.util.DialogUtils
import com.waitou.imgloader_lib.DisplayOptions
import com.waitou.imgloader_lib.ImageLoader
import com.waitou.three_library.share.ShareInfo
import com.waitou.towards.R
import com.waitou.towards.bean.JokeInfo
import kotlinx.android.synthetic.main.item_imagejoke.view.*
import kotlinx.android.synthetic.main.item_textjoke.view.share

/**
 * auth aboom
 * date 2019-05-20
 */
class ImageJokeDelegate : AdapterDelegate() {

    override fun isForViewType(displayable: Displayable): Boolean {
        return displayable is JokeInfo && !TextUtils.isEmpty(displayable.url)
    }

    override fun layout(): Int {
        return R.layout.item_imagejoke
    }

    override fun convert(helper: BaseViewHolder, data: Displayable, position: Int) {
        val item = data as JokeInfo

        helper.itemView.content.text = item.content
        ImageLoader.displayImage(helper.itemView.img,item.url, DisplayOptions.build()
                .error(R.drawable.error_place).placeholder(R.drawable.default_place))
        helper.itemView.time.text = TimeUtils.millis2String(item.unixtime * 1000L)
        helper.itemView.share.setOnClickListener {
            val shareInfo = ShareInfo()
            shareInfo.content = item.content
            shareInfo.imageUrl = item.url
            shareInfo.type = ShareInfo.TEXT_AND_IMAGE
            DialogUtils.showShareDialog(context as FragmentActivity?, shareInfo) { ToastUtils.showShort("分享成功") }
        }
    }
}