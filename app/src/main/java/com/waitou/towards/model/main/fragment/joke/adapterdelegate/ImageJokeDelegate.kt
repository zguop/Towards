package com.waitou.towards.model.main.fragment.joke.adapterdelegate

import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.to.aboomy.recycler_lib.AdapterDelegate
import com.to.aboomy.recycler_lib.Displayable
import com.waitou.basic_lib.util.DialogUtils
import com.waitou.three_library.share.ShareInfo
import com.waitou.towards.R
import com.waitou.towards.bean.JokeInfo
import kotlinx.android.synthetic.main.item_textjoke.view.*

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

    override fun convert(helper: BaseViewHolder?, data: Displayable?, position: Int) {
        super.convert(helper, data, position)
        val item = data as JokeInfo
        helper?.let {
            it.itemView.share.setOnClickListener {
                val shareInfo = ShareInfo()
                shareInfo.content = item.content
                shareInfo.imageUrl = item.url
                shareInfo.type = ShareInfo.TEXT_AND_IMAGE
                DialogUtils.showShareDialog(context as FragmentActivity?, shareInfo) { ToastUtils.showShort("分享成功") }
            }
        }
    }
}