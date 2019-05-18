package com.waitou.towards.model.main.fragment.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.to.aboomy.recycler_lib.MultipleRecyclerAdapter
import com.to.aboomy.recycler_lib.Place
import com.to.aboomy.recycler_lib.PlaceDelegate
import com.to.aboomy.recycler_lib.PullRecyclerView
import com.waitou.net_library.model.APIResult
import com.waitou.towards.R
import com.waitou.towards.bean.CanInfo
import com.waitou.towards.bean.HomeDataInfo
import com.waitou.towards.model.main.fragment.home.adapterdelegate.BannerDelegate
import com.waitou.towards.model.main.fragment.home.adapterdelegate.BottomTipsDelegate
import com.waitou.towards.model.main.fragment.home.adapterdelegate.HomeFunctionDelegate
import com.waitou.towards.model.main.fragment.home.adapterdelegate.RoundImageDelegate
import com.waitou.wt_library.base.LazyFragment
import com.waitou.wt_library.base.IView
import com.waitou.wt_library.manager.ViewManager

/**
 * auth aboom
 * date 2019-05-05
 */
class HomeNewFragment : LazyFragment(), IView {

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
        adapter.addDelegate(
                BannerDelegate(),
                HomeFunctionDelegate(),
                RoundImageDelegate(),
                BottomTipsDelegate(),
                PlaceDelegate()
        )
        list.setAdapter(adapter)
        return list
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        val coordinatorLayout = CoordinatorLayout(activity!!)
        coordinatorLayout.setStatusBarBackgroundColor(ActivityCompat.getColor(activity!!, R.color.bg_grey))
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

    private fun bindUI(apiResult: APIResult<MutableList<HomeDataInfo>>?) {
        apiResult?.let {
            if (!it.isSuccess) {
                manager.showFailed()
                return
            }
            val data = it.data
            if (data.isEmpty()) {
                manager.showEmpty()
                return
            }
            manager.showContent()
            adapter.replaceData(data)
            adapter.addData(0, Place.createPlace(SizeUtils.dp2px(10f)))
            adapter.addData(CanInfo())
        }
    }
}


