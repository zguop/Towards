package com.waitou.towards.model.main.fragment.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.billy.android.loading.Gloading
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SizeUtils
import com.to.aboomy.recycler_lib.adapter.MultipleAdapter
import com.to.aboomy.recycler_lib.species.Place
import com.to.aboomy.recycler_lib.species.PlaceDelegate
import com.to.aboomy.theme_lib.utils.ThemeUtils
import com.waitou.net_library.model.APIResult
import com.waitou.towards.R
import com.waitou.towards.bean.CanInfo
import com.waitou.towards.bean.HomeDataInfo
import com.waitou.towards.model.QrScanActivity
import com.waitou.towards.model.main.fragment.home.adapterdelegate.BannerDelegate
import com.waitou.towards.model.main.fragment.home.adapterdelegate.BottomTipsDelegate
import com.waitou.towards.model.main.fragment.home.adapterdelegate.HomeFunctionDelegate
import com.waitou.towards.model.main.fragment.home.adapterdelegate.RoundImageDelegate
import com.waitou.wt_library.base.IView
import com.waitou.wt_library.base.LazyFragment
import com.waitou.wt_library.manager.StateViewManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * auth aboom
 * date 2019-05-05
 */
class HomeNewFragment : LazyFragment(), IView {

    private lateinit var holder: Gloading.Holder
    private lateinit var homeNewViewModule: HomeNewViewModule
    private lateinit var adapter: MultipleAdapter

    override fun run() {
        reloadData()
    }

    override fun getContentView(): View {
        val inflate = View.inflate(activity!!, R.layout.fragment_home, null)
        inflate.list.setLayoutManager(LinearLayoutManager(activity))
        adapter = MultipleAdapter()
        inflate.list.setAdapter(adapter)
        inflate.list.swipeRefreshLayout.setColorSchemeColors(ThemeUtils.getColorPrimary(activity))
        adapter.addDelegate(
                BannerDelegate(),
                HomeFunctionDelegate(),
                RoundImageDelegate(),
                BottomTipsDelegate(),
                PlaceDelegate()
        )
        inflate.list.setOnRefreshListener { homeNewViewModule.loadNewHomeData() }
        inflate.search.editView.isFocusable = false
        inflate.search.editView.setOnClickListener {

        }
        inflate.postDelayed({ translationScaleSearch() }, 1000)
        inflate.qrCode.setOnClickListener {
            ActivityUtils.startActivity(QrScanActivity::class.java)
        }
        return inflate
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        holder = StateViewManager.wrapStateController(this, false)
        return holder.wrapper
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeNewViewModule = ViewModelProviders.of(this)[HomeNewViewModule::class.java]
        homeNewViewModule.mutableLiveData.observe(this@HomeNewFragment, Observer {
            it?.run { bindUI(it) }
        })
    }

    override fun visibleCall() {
        super.visibleCall()
        reloadData()
    }

    private fun reloadData() {
        holder.showLoading()
        homeNewViewModule.loadNewHomeData()
    }

    private fun bindUI(apiResult: APIResult<MutableList<HomeDataInfo>>?) {
        try {
            apiResult?.let {
                list.loadComplete(true)
                if (!it.isSuccess) {
                    holder.showLoadFailed()
                    return
                }
                val data = it.data
                if (data.isEmpty()) {
                    holder.showEmpty()
                    return
                }
                holder.showLoadSuccess()
                adapter.replaceData(data)
                adapter.addData(0, Place.createPlaceDp(15, Color.WHITE))
                adapter.addData(CanInfo())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun applySkin() {
        adapter.notifyDataSetChanged()
        list.swipeRefreshLayout.setColorSchemeColors(ThemeUtils.getColorPrimary(activity))
    }

    private fun translationScaleSearch() {
        list.contentView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val searchLayoutParams = search.layoutParams as ViewGroup.MarginLayoutParams
            //向上滑动顶部的距离
            private val TOP_MARGIN = appName.top - appName.top / 4 + statusBar.height * 1.0f
            //原来的初始位置距离
            private val MAX_MARGIN = search.top
            //搜索框的完整宽度
            private val MAX_WIDTH = search.width
            //搜索框的最小宽度
            private val MIN_WIDTH = search.width - appName.width - SizeUtils.dp2px(16f)
            //累加距离
            private var mDy = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mDy += dy
                var searchLayoutNewTopMargin = MAX_MARGIN - mDy * 0.5f
                if (searchLayoutNewTopMargin < TOP_MARGIN) {
                    searchLayoutNewTopMargin = TOP_MARGIN
                }
                val alpha = (MAX_MARGIN - mDy * 1.0f) / searchLayoutNewTopMargin
                titleView.alpha = alpha
                titleView.visibility = if (alpha > 0) View.VISIBLE else View.GONE


                qrCode.alpha = alpha
                qrCode.visibility = if (alpha > 0) View.VISIBLE else View.GONE

                var searchLayoutNewWidth = MAX_WIDTH - mDy * 0.5f//此处 * 1.3f 可以设置搜索框宽度缩放的速率
                if (searchLayoutNewWidth < MIN_WIDTH) {
                    searchLayoutNewWidth = MIN_WIDTH.toFloat()
                }

                val appNameAlpha = (MAX_WIDTH - mDy * 2.0f) / searchLayoutNewWidth
                appName.alpha = if (1 - appNameAlpha < 0) 1f else 1 - appNameAlpha
                appName.visibility = if (appName.alpha > 0) View.VISIBLE else View.INVISIBLE

                searchLayoutParams.topMargin = searchLayoutNewTopMargin.toInt()
                searchLayoutParams.width = searchLayoutNewWidth.toInt()
                search.layoutParams = searchLayoutParams
            }
        })
    }
}


