package com.waitou.towards.model.main.fragment.joke.adapterdelegate

import android.text.TextUtils
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.to.aboomy.recycler_lib.adapter.Displayable
import com.to.aboomy.recycler_lib.delegate.AdapterDelegate
import com.waitou.basic_lib.util.DialogUtils
import com.waitou.three_library.share.ShareInfo
import com.waitou.towards.R
import com.waitou.towards.bean.JokeInfo
import kotlinx.android.synthetic.main.item_textjoke.view.*

/**
 * auth aboom
 * date 2019-05-20
 */
class TextJokeDelegate : AdapterDelegate() {
    override fun isForViewType(displayable: Displayable): Boolean {
        return displayable is JokeInfo && TextUtils.isEmpty(displayable.url)
    }

    override fun layout(): Int {
        return R.layout.item_textjoke
    }

    override fun convert(helper: BaseViewHolder, data: Displayable, position: Int) {
        val item = data as JokeInfo
        helper.itemView.content.text = item.content
        helper.itemView.time.text = TimeUtils.millis2String(item.unixtime * 1000L)
        helper.itemView.share.setOnClickListener {
            val shareInfo = ShareInfo()
            shareInfo.content = item.content
            shareInfo.type = ShareInfo.TEXT
            DialogUtils.showShareDialog(context as FragmentActivity?, shareInfo) { ToastUtils.showShort("分享成功") }
        }
    }
}
