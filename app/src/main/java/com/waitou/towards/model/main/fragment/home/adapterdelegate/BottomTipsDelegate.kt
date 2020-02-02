package com.waitou.towards.model.main.fragment.home.adapterdelegate

import com.chad.library.adapter.base.BaseViewHolder
import com.to.aboomy.recycler_lib.adapter.Displayable
import com.to.aboomy.recycler_lib.delegate.AdapterDelegate
import com.waitou.towards.R
import com.waitou.towards.bean.CanInfo

/**
 * auth aboom
 * date 2019-05-18
 */
class BottomTipsDelegate : AdapterDelegate() {
    override fun convert(helper: BaseViewHolder?, data: Displayable?, position: Int) {

    }

    override fun isForViewType(displayable: Displayable): Boolean {
        return displayable is CanInfo
    }

    override fun layout(): Int {
        return R.layout.item_bottom
    }
}
