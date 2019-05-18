package com.waitou.towards.model.main.fragment.home.adapterdelegate

import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexboxLayout
import com.to.aboomy.recycler_lib.AdapterDelegate
import com.to.aboomy.recycler_lib.Displayable
import com.waitou.imgloader_lib.DisplayOptions
import com.waitou.imgloader_lib.ImageLoader
import com.waitou.towards.R
import com.waitou.towards.bean.FunctionInfo
import com.waitou.towards.bean.HomeDataInfo
import com.waitou.towards.view.CustomCircleImageView
import com.waitou.widget_lib.bindview.BindViewHelper
import com.waitou.widget_lib.bindview.OnSimpleBindData
import com.waitou.widget_lib.bindview.ViewHolder

/**
 * auth aboom
 * date 2019-05-18
 */
class HomeFunctionDelegate : AdapterDelegate() {

    var bindViewHelper: BindViewHelper<FunctionInfo>? = null

    override fun layout(): Int {
        return R.layout.item_view_fiex_box
    }

    override fun isForViewType(item: Displayable): Boolean {
        return item is HomeDataInfo && item.template == 1
    }

    override fun convert(helper: BaseViewHolder, data: Displayable, position: Int) {
        val info = data as HomeDataInfo
        if (bindViewHelper == null) {
            val flexboxLayout = helper.getView<FlexboxLayout>(R.id.flex_box)
            flexboxLayout.setBackgroundColor(Color.WHITE)
            flexboxLayout.flexWrap = FlexboxLayout.FLEX_WRAP_NOWRAP
            flexboxLayout.justifyContent = FlexboxLayout.JUSTIFY_CONTENT_SPACE_AROUND
            bindViewHelper = BindViewHelper(flexboxLayout)
        }
        bindViewHelper?.let {
            it.setBindData(object : OnSimpleBindData<FunctionInfo>() {
                override fun onBind(pos: Int, itemCount: Int, t: FunctionInfo?, holder: ViewHolder?) {
                    holder?.apply {
                        val img = getView<CustomCircleImageView>(R.id.img)
                        ImageLoader.displayImage(img, t?.picUrl, DisplayOptions.build().setPlaceholder(R.drawable.base_ic_retry))
                        getView<TextView>(R.id.text).text = t?.description
                        convertView.setOnClickListener {

                        }
                    }
                }

                override fun onLayout(): Int {
                    return R.layout.item_home_function
                }
            })
            it.replaceData(info.templateJson)
        }
    }
}
