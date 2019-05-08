package com.waitou.towards.model.main.fragment.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexboxLayout
import com.to.aboomy.banner.QyBanner
import com.to.aboomy.banner.QyPagerAdapter
import com.to.aboomy.banner.ScalePageTransformer
import com.to.aboomy.recycler_lib.*
import com.to.aboomy.theme_lib.config.ThemeUtils
import com.waitou.imgloader_lib.DisplayOptions
import com.waitou.imgloader_lib.HsRoundImageView
import com.waitou.imgloader_lib.ImageLoader
import com.waitou.net_library.model.APIResult
import com.waitou.towards.R
import com.waitou.towards.bean.CanInfo
import com.waitou.towards.bean.FunctionInfo
import com.waitou.towards.bean.HomeDataInfo
import com.waitou.towards.view.CustomCircleImageView
import com.waitou.widget_lib.bindview.BindViewHelper
import com.waitou.widget_lib.bindview.OnSimpleBindData
import com.waitou.widget_lib.bindview.ViewHolder
import com.waitou.wt_library.base.BaseFragment
import com.waitou.wt_library.base.IView
import com.waitou.wt_library.manager.ViewManager
import com.waitou.wt_library.view.Indicator

/**
 * auth aboom
 * date 2019-05-05
 */
class HomeNewFragment : BaseFragment(), IView {

    private lateinit var manager: ViewManager
    private lateinit var homeNewViewModule: HomeNewViewModule
    private lateinit var adapter: MultipleRecyclerAdapter

    override fun run(): Runnable {
        return Runnable { reloadData() }
    }

    override fun getContentView(): View {
        val list = PullRecyclerView(activity!!)
        list.setLayoutManager(LinearLayoutManager(activity))
        adapter = MultipleRecyclerAdapter()
        adapter.addDelegate(object : AdapterDelegate() {
            override fun layout(): Int {
                return R.layout.item_view_banner
            }

            override fun isForViewType(item: Displayable): Boolean {
                return item is HomeDataInfo && item.template == 0
            }

            override fun convert(helper: BaseViewHolder, data: Displayable, position: Int) {
                val info = data as HomeDataInfo
                val banner = helper.getView<QyBanner>(R.id.banner)
                val indicator = Indicator(activity)
                        .setGravity(Gravity.CENTER)
                        .setIndicatorInColor(ThemeUtils.getThemeAttrColor(activity!!, R.attr.colorPrimary))
                banner.setPageMargins(SizeUtils.dp2px(30f), SizeUtils.dp2px(10f), SizeUtils.dp2px(30f), SizeUtils.dp2px(20f), SizeUtils.dp2px(10f))
                banner.setPageTransformer(true, ScalePageTransformer(0.8f))
                banner.setIndicator(indicator)
                banner.setAdapter(object : QyPagerAdapter<FunctionInfo>(info.templateJson) {
                    override fun newView(context: Context, realPosition: Int): View? {
                        val view = View.inflate(context, R.layout.item_banner_image, null)
                        val image = view.findViewById<ImageView>(R.id.img)
                        ImageLoader.displayImage(image, mData[realPosition].picUrl)
                        return view
                    }
                })
            }
        }, object : AdapterDelegate() {
            override fun layout(): Int {
                return R.layout.item_view_fiex_box
            }

            override fun isForViewType(item: Displayable): Boolean {
                return item is HomeDataInfo && item.template == 1
            }

            var bindViewHelper: BindViewHelper<FunctionInfo>? = null

            override fun convert(helper: BaseViewHolder, data: Displayable, position: Int) {
                val info = data as HomeDataInfo
                if (bindViewHelper == null) {
                    val flexboxLayout = helper.getView<FlexboxLayout>(R.id.flex_box)
                    flexboxLayout.setBackgroundColor(Color.WHITE)
                    flexboxLayout.flexWrap = FlexboxLayout.FLEX_WRAP_NOWRAP
                    flexboxLayout.justifyContent = FlexboxLayout.JUSTIFY_CONTENT_SPACE_AROUND
                    bindViewHelper = BindViewHelper(flexboxLayout)
                }
                bindViewHelper!!.setBindData(object : OnSimpleBindData<FunctionInfo>() {
                    override fun onBind(pos: Int, itemCount: Int, t: FunctionInfo, holder: ViewHolder) {
                        val img = holder.getView<CustomCircleImageView>(R.id.img)
                        ImageLoader.displayImage(img, t.picUrl, DisplayOptions.build().setPlaceholder(R.drawable.base_ic_retry))
                        holder.getView<TextView>(R.id.text).text = t.description
                        holder.convertView.setOnClickListener {

                        }
                    }

                    override fun onLayout(): Int {
                        return R.layout.item_home_function
                    }
                })
                bindViewHelper!!.replaceData(info.templateJson)
            }
        }, object : AdapterDelegate() {
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
        }, object : AdapterDelegate() {
            override fun isForViewType(displayable: Displayable): Boolean {
                return displayable is CanInfo
            }

            override fun layout(): Int {
                return R.layout.item_bottom
            }

        }, PlaceDelegate())
        list.setAdapter(adapter)
        return list
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val coordinatorLayout = CoordinatorLayout(activity!!)
        coordinatorLayout.setStatusBarBackgroundColor(ActivityCompat.getColor(activity!!,R.color.bg_grey))
        manager = ViewManager.getManager(coordinatorLayout)
        manager.wrapXStateController(this, true)
        homeNewViewModule = ViewModelProviders.of(this)[HomeNewViewModule::class.java]
        homeNewViewModule.mutableLiveData.observe(this@HomeNewFragment, Observer {
            it?.run { bindUI(it) }
        })
        reloadData()
        return coordinatorLayout
    }

    private fun reloadData() {
        manager.showLoading()
        homeNewViewModule.loadNewHomeData()
    }

    private fun bindUI(apiResult: APIResult<List<HomeDataInfo>>) {
        if (!apiResult.isSuccess) {
            manager.showFailed()
            return
        }
        var data = apiResult.data
        if (data.isEmpty()) {
            manager.showEmpty()
            return
        }
        manager.showContent()
        adapter.replaceData(data)
        adapter.addData(CanInfo())
    }
}


