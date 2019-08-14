package com.waitou.towards.model.graffiti

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.waitou.towards.R
import com.waitou.towards.bean.GraffitiToolInfo
import kotlinx.android.synthetic.main.item_select_tool.view.*
import java.util.*

/**
 * auth aboom
 * date 2019-08-13
 */
class GraffitiToolAdapter(private val shopType: Int) : BaseQuickAdapter<GraffitiToolInfo, BaseViewHolder>(R.layout.item_select_tool) {
    init {
        val data = ArrayList<GraffitiToolInfo>()
        for (value in GraffitiToolEnum.values()) {
            data.add(GraffitiToolInfo(value.redId, value.tool))
        }
        data.add(GraffitiToolInfo(0, ""))
        addData(data)
    }

    var callback: ((Int) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: GraffitiToolInfo) {
        helper.itemView.text.text = item.tool
        helper.itemView.text.isSelected = helper.adapterPosition == shopType
        helper.itemView.icon.visibility = if (helper.adapterPosition == shopType) View.VISIBLE else View.GONE
        //adapterPosition其实对应的就是那个type
        helper.itemView.setOnClickListener {
            if (item.tool.isEmpty()) return@setOnClickListener
            callback?.invoke(helper.adapterPosition)
        }
    }
}
