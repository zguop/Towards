package com.waitou.towards.model.main.fragment.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.billy.android.loading.Gloading
import com.to.aboomy.recycler_lib.MultipleRecyclerAdapter
import com.to.aboomy.recycler_lib.Place
import com.to.aboomy.recycler_lib.PlaceDelegate
import com.to.aboomy.recycler_lib.PullRecyclerView
import com.to.aboomy.theme_lib.utils.ThemeUtils
import com.waitou.net_library.model.APIResult
import com.waitou.towards.bean.CanInfo
import com.waitou.towards.bean.HomeDataInfo
import com.waitou.towards.model.main.fragment.home.adapterdelegate.BannerDelegate
import com.waitou.towards.model.main.fragment.home.adapterdelegate.BottomTipsDelegate
import com.waitou.towards.model.main.fragment.home.adapterdelegate.HomeFunctionDelegate
import com.waitou.towards.model.main.fragment.home.adapterdelegate.RoundImageDelegate
import com.waitou.wt_library.base.IView
import com.waitou.wt_library.base.LazyFragment
import com.waitou.wt_library.manager.RecyclerViewManager
import com.waitou.wt_library.manager.StateViewManager

/**
 * auth aboom
 * date 2019-05-05
 */
class HomeNewFragment : LazyFragment(), IView {

    private lateinit var list: PullRecyclerView
    private lateinit var holder: Gloading.Holder
    private lateinit var homeNewViewModule: HomeNewViewModule
    private lateinit var adapter: MultipleRecyclerAdapter

    override fun run(): Runnable {
        return Runnable { reloadData() }
    }

    override fun getContentView(): View {
        list = RecyclerViewManager.attachViewGetRefresh(activity)
        list.swipeRefreshLayout.setColorSchemeColors(ThemeUtils.getColorPrimary(activity))
        adapter = list.contentView.adapter as MultipleRecyclerAdapter
        adapter.addDelegate(
                BannerDelegate(),
                HomeFunctionDelegate(),
                RoundImageDelegate(),
                BottomTipsDelegate(),
                PlaceDelegate()
        )
        list.setOnRefreshListener { homeNewViewModule.loadNewHomeData() }
        return list
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        holder = StateViewManager.wrapXStateController(this, false)
        homeNewViewModule = ViewModelProviders.of(this)[HomeNewViewModule::class.java]
        homeNewViewModule.mutableLiveData.observe(this@HomeNewFragment, Observer {
            it?.run { bindUI(it) }
        })
        return holder.wrapper
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
}


