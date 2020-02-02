package com.waitou.towards.model.main.fragment.home.adapterdelegate

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.to.aboomy.banner.Banner
import com.to.aboomy.banner.IndicatorView
import com.to.aboomy.banner.ScaleInTransformer
import com.to.aboomy.recycler_lib.adapter.Displayable
import com.to.aboomy.recycler_lib.delegate.AdapterDelegate
import com.to.aboomy.theme_lib.utils.ThemeUtils
import com.umeng.socialize.utils.DeviceConfig.context
import com.waitou.imgloader_lib.ImageLoader
import com.waitou.towards.R
import com.waitou.towards.bean.FunctionInfo
import com.waitou.towards.bean.HomeDataInfo

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

        val layoutParams = banner.getChildAt(0).layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin = SizeUtils.dp2px(20f)
        layoutParams.bottomMargin = SizeUtils.dp2px(20f)

        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        params.addRule(RelativeLayout.CENTER_HORIZONTAL)
        val indicator = IndicatorView(context)
                .setParams(params)
                .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_CIRCLE_RECT)
                .setIndicatorColor(ColorUtils.getColor(R.color.color_666666))
                .setIndicatorSelectorColor(ThemeUtils.getThemeAttrColor(context, R.attr.colorPrimary))
        banner.setPageTransformer(true, ScaleInTransformer(0.8f))
        banner.setPageMargin(SizeUtils.dp2px(10f), -SizeUtils.dp2px(14f))
                .setIndicator(indicator)
                .setHolderCreator { context, _, o ->
                    val view = View.inflate(context, R.layout.item_banner_image, null)
                    val image = view.findViewById<ImageView>(R.id.img)
                    ImageLoader.displayImage(image, (o as FunctionInfo).picUrl)
                    view
                }
                .setPages(info.templateJson)

    }
}
