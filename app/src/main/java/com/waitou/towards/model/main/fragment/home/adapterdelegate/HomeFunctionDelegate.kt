package com.waitou.towards.model.main.fragment.home.adapterdelegate

import android.graphics.Color
import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexboxLayout
import com.to.aboomy.recycler_lib.adapter.Displayable
import com.to.aboomy.recycler_lib.delegate.AdapterDelegate
import com.waitou.imgloader_lib.DisplayOptions
import com.waitou.imgloader_lib.ImageLoader
import com.waitou.towards.R
import com.waitou.towards.bean.HomeDataInfo
import kotlinx.android.synthetic.main.item_home_function.view.*

/**
 * auth aboom
 * date 2019-05-18
 */
class HomeFunctionDelegate : AdapterDelegate() {

    override fun layout(): Int {
        return R.layout.item_view_fiex_box
    }

    override fun isForViewType(item: Displayable): Boolean {
        return item is HomeDataInfo && item.template == 1
    }

    override fun convert(helper: BaseViewHolder, data: Displayable, position: Int) {
        val info = data as HomeDataInfo
        val flexboxLayout = helper.getView<FlexboxLayout>(R.id.flex_box)
        flexboxLayout.setBackgroundColor(Color.WHITE)
        flexboxLayout.flexWrap = FlexboxLayout.FLEX_WRAP_NOWRAP
        flexboxLayout.justifyContent = FlexboxLayout.JUSTIFY_CONTENT_SPACE_AROUND
        flexboxLayout.removeAllViews()
        info.templateJson.forEach {
            val inflate = View.inflate(context, R.layout.item_home_function, null)
            ImageLoader.displayImage(inflate.img, it.picUrl, DisplayOptions.build().placeholder(R.drawable.base_ic_retry))
            inflate.text.text = it.description
            inflate.setOnClickListener {

            }
            flexboxLayout.addView(inflate)
        }

    }
}
