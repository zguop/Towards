package com.waitou.towards.model.main.fragment.home.adapterdelegate

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.to.aboomy.banner.Banner
import com.to.aboomy.banner.LoopPagerAdapter
import com.to.aboomy.banner.ScalePageTransformer
import com.to.aboomy.recycler_lib.AdapterDelegate
import com.to.aboomy.recycler_lib.Displayable
import com.to.aboomy.theme_lib.utils.ThemeUtils
import com.waitou.imgloader_lib.ImageLoader
import com.waitou.towards.R
import com.waitou.towards.bean.FunctionInfo
import com.waitou.towards.bean.HomeDataInfo
import com.waitou.wt_library.view.Indicator

/**
 * auth aboom
 * date 2019-05-18
 */
class BannerDelegate : AdapterDelegate() {
    override fun layout(): Int {
        return R.layout.item_view_banner
    }

    override fun isForViewType(item: Displayable): Boolean {
        return item is HomeDataInfo && item.template == 0
    }

    override fun convert(helper: BaseViewHolder, data: Displayable, position: Int) {
        val info = data as HomeDataInfo
        val banner = helper.getView<Banner>(R.id.banner)
        val indicator = Indicator(context)
                .setGravity(Gravity.CENTER)
                .setIndicatorInColor(ThemeUtils.getThemeAttrColor(context, R.attr.colorPrimary))
        banner.setPageMargins(SizeUtils.dp2px(30f), SizeUtils.dp2px(10f), SizeUtils.dp2px(30f), SizeUtils.dp2px(20f), SizeUtils.dp2px(10f))
        banner.setPageTransformer(true, ScalePageTransformer(0.8f))
        banner.setIndicator(indicator)
        banner.setAdapter(object : LoopPagerAdapter<FunctionInfo>(info.templateJson) {
            override fun newView(context: Context?, realPosition: Int, t: FunctionInfo?): View {
                val view = View.inflate(context, R.layout.item_banner_image, null)
                val image = view.findViewById<ImageView>(R.id.img)
                ImageLoader.displayImage(image, t?.picUrl)
                return view
            }
        })
    }
}
