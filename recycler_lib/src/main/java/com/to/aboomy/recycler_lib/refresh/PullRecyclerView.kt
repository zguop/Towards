package com.to.aboomy.recycler_lib.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.to.aboomy.recycler_lib.R
import kotlinx.android.synthetic.main.rv_view_pull_refresh.view.*

/**
 * auth aboom
 * date 2020/6/21
 */

class PullRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs), OnRefreshLoadMoreListener {

    var pageSize = 20
    var currentPage = 1
        internal set

    private var loadMore = false
    private var lastPage = false
    private var onRefresh: OnRefreshListener? = null
    private var onLoadMore: OnLoadMoreListener? = null


    init {
        View.inflate(context, R.layout.rv_view_pull_refresh, this)
        refreshLayout.setEnableRefresh(false) //是否开启下拉刷新功能
        refreshLayout.setEnableLoadMore(false) //是否启用上拉加载功能
    }

    fun getRecyclerView(): RecyclerView {
        return recyclerView
    }

    fun getRefreshLayout(): RefreshLayout {
        return refreshLayout
    }

    fun loadComplete(dataSize: Int) {
        loadComplete(dataSize < pageSize)
    }

    fun loadComplete(isLastPage: Boolean) {
        lastPage = isLastPage
        upFetch(true)
    }

    fun loadFail() {
        upFetch(false)
    }

    /**
     * 设置加载更多
     */
    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        onLoadMore = onLoadMoreListener
        refreshLayout.setEnableLoadMore(true)
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.refreshFooter?:refreshLayout.setRefreshFooter(ClassicsFooter(context))
    }

    /**
     * 设置下啦刷新
     */
    fun setOnRefreshListener(onRefreshListener: OnRefreshListener) {
        onRefresh = onRefreshListener
        refreshLayout.setEnableRefresh(true)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.refreshHeader ?: refreshLayout.setRefreshHeader(MaterialHeader(context))
    }

    /**
     * 同时设置加载更多，下啦刷新
     */
    fun setRefreshAndLoadMoreListener(onRefreshAndLoadMoreListener: OnRefreshAndLoadMoreListener) {
        setOnRefreshListener(onRefreshAndLoadMoreListener)
        setOnLoadMoreListener(onRefreshAndLoadMoreListener)
    }

    /**
     * 下啦监听器
     */
    interface OnRefreshListener {
        fun onRefresh(pull: PullRecyclerView)
    }

    /**
     * 上啦监听器
     */
    interface OnLoadMoreListener {
        fun onLoadMore(pull: PullRecyclerView)
    }

    interface OnRefreshAndLoadMoreListener : OnRefreshListener,
        OnLoadMoreListener

    private fun upFetch(isComplete: Boolean) {
        if (loadMore) {
            loadMore = false
            if (!isComplete) {
                currentPage--
            }
            if (lastPage) {
                refreshLayout.finishLoadMoreWithNoMoreData()
            } else {
                refreshLayout.finishLoadMore(isComplete)
            }
            onRefresh?.let {
                refreshLayout.setEnableRefresh(true)
            }
        } else {
            refreshLayout.finishRefresh(isComplete)
            refreshLayout.setNoMoreData(lastPage)
            onLoadMore?.let {
                refreshLayout.setEnableLoadMore(true)
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        currentPage = 1
        onLoadMore?.let {
            refreshLayout.setEnableLoadMore(false)
        }
        onRefresh?.onRefresh(this)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        loadMore = true
        currentPage++
        onRefresh?.let {
            refreshLayout.setEnableRefresh(false)
        }
        onLoadMore?.onLoadMore(this)
    }
}