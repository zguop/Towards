package com.waitou.towards.model.main.fragment.joke

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.billy.android.loading.Gloading
import com.blankj.utilcode.util.ObjectUtils
import com.to.aboomy.recycler_lib.PullRecyclerView
import com.to.aboomy.recycler_lib.adapter.MultipleAdapter
import com.to.aboomy.recycler_lib.species.Place
import com.to.aboomy.recycler_lib.species.PlaceDelegate
import com.waitou.net_library.model.APIResult
import com.waitou.towards.bean.JokeInfo
import com.waitou.towards.common.Values
import com.waitou.towards.model.main.fragment.joke.adapterdelegate.ImageJokeDelegate
import com.waitou.towards.model.main.fragment.joke.adapterdelegate.TextJokeDelegate
import com.waitou.wt_library.base.IView
import com.waitou.wt_library.base.LazyFragment
import com.waitou.wt_library.manager.RecyclerViewManager
import com.waitou.wt_library.manager.StateViewManager

/**
 * auth aboom
 * date 2019-05-19
 */
class JokeLotFragment : LazyFragment(), IView, PullRecyclerView.OnRefreshAndLoadMoreListener {

    companion object {
        fun newInstance(type: Int): Fragment {
            val fragment = JokeLotFragment()
            val bundle = Bundle()
            bundle.putInt(Values.JOKE_CONTENT_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var pullRecyclerView: PullRecyclerView
    private lateinit var holder: Gloading.Holder
    private lateinit var adapter: MultipleAdapter

    private val viewModule by lazy { ViewModelProviders.of(this@JokeLotFragment)[JokeLotViewModule::class.java] }
    private val type by lazy { arguments?.getInt(Values.JOKE_CONTENT_TYPE) }

    override fun run()  {
        visibleCall()
    }

    override fun getContentView(): View {
        pullRecyclerView = RecyclerViewManager.attachViewGetRefresh(activity)
        pullRecyclerView.distanceOffsetBar()
        pullRecyclerView.setRefreshAndLoadMoreListener(this)
        adapter = pullRecyclerView.contentView.adapter as MultipleAdapter
        adapter.addDelegate(ImageJokeDelegate(), TextJokeDelegate(), PlaceDelegate())
        return pullRecyclerView
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        holder = StateViewManager.wrapStateController(this, false)
        return holder.wrapper
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModule.mutableLiveData.observe(this@JokeLotFragment, Observer {
            it?.run { bindUI(it) }
        })
    }

    private fun bindUI(it: APIResult<MutableList<JokeInfo>>) {
        pullRecyclerView.loadComplete(ObjectUtils.isEmpty(it.data) || it.data.size < 10)
        if (it.isFailed) {
            if (it.isFirstPage)
                holder.showLoadFailed()
            return
        }
        if (it.pageIndex == 1) {
            adapter.replaceData(it.data)
            adapter.addData(0, Place.createPlaceDp(78))
        } else {
            adapter.addData(it.data)
        }
        holder.showLoadSuccess()
    }

    override fun visibleCall() {
        super.visibleCall()
        holder.showLoading()
        onRefresh()
    }

    override fun onRefresh() {
        viewModule.loadJokeData(type)
    }

    override fun onLoadMore(page: Int) {
        viewModule.loadJokeData(type, page)
    }
}