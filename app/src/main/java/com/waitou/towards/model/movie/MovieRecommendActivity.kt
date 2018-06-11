package com.waitou.towards.model.movie

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.to.aboomy.theme_lib.config.ThemeUtils
import com.waitou.net_library.model.Displayable
import com.waitou.towards.R
import com.waitou.towards.bean.BannerAdapterInfo
import com.waitou.towards.bean.MovieInfo
import com.waitou.towards.bean.MovieResInfo
import com.waitou.towards.bean.TitleInfo
import com.waitou.towards.common.ExtraValue
import com.waitou.towards.databinding.ActivityMovieBinding
import com.waitou.wt_library.base.XActivity
import com.to.aboomy.utils_lib.USize
import com.to.aboomy.utils_lib.Util
import com.waitou.wt_library.recycler.LayoutManagerUtil
import com.waitou.wt_library.recycler.adapter.MultiTypeAdapter
import com.waitou.wt_library.router.Router
import com.waitou.wt_library.view.viewpager.SingleViewPagerAdapter

/**
 * Created by waitou on 17/5/25.
 * 影视推荐
 */
class MovieRecommendActivity : XActivity<MovieTelevisionPresenter, ActivityMovieBinding>() {

    private var adapter: MultiTypeAdapter<Displayable>? = null
    private var movieResInfo: MovieResInfo? = null

    override fun defaultLoading(): Boolean {
        return true
    }

    override fun createPresenter(): MovieTelevisionPresenter {
        return MovieTelevisionPresenter()
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_movie
    }

    override fun initData(savedInstanceState: Bundle?) {
        initMenuActionBar("影视精选", "专题", {
            if (movieResInfo != null) {
                Router.newIntent()
                        .from(this)
                        .to(MovieProjectActivity::class.java)
                        .putSerializable(ExtraValue.MOVIE_PROJECT, movieResInfo)
                        .launch()
            }
        })
        (xBinding.xContentLayout.layoutParams as CoordinatorLayout.LayoutParams).behavior = null
        Util.transparentStatusBar(this)
        xBinding.toolbar.layoutParams.height = USize.dip2pxInt(73f)
        xBinding.toolbar.setPadding(0, USize.dip2pxInt(25f), 0, 0)
        xBinding.appbar.visibility = View.GONE
        adapter = MultiTypeAdapter<Displayable>(this)
        adapter!!.addViewTypeToLayoutMap(0, R.layout.item_banner_search)
        adapter!!.addViewTypeToLayoutMap(1, R.layout.item_movie_title)
        adapter!!.addViewTypeToLayoutMap(2, R.layout.item_movie_piture)
        binding.manager = LayoutManagerUtil.getVerticalLayoutManager(this)!!
        binding.adapter = adapter
        binding.xList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val argbEvaluator = ArgbEvaluator()
            private val height = USize.dip2pxInt(175f)
            private val startColor = 0x00ffffff
            private val endColor = ThemeUtils.getThemeAttrColor(this@MovieRecommendActivity, R.attr.colorPrimary)
            private var mDy = 0
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mDy += dy
                if (mDy < height) {
                    val alpha = mDy * 1.0f / height
                    val color = argbEvaluator.evaluate(alpha, startColor, endColor) as Int
                    xBinding.appbar.setBackgroundColor(color)
                    xBinding.appbar.visibility = if (alpha > 0) View.VISIBLE else View.GONE
                    xBinding.toolbar.alpha = alpha
                } else {
                    xBinding.appbar.setBackgroundColor(endColor)
                    xBinding.toolbar.alpha = 1.0f
                }
            }
        })
        reloadData()
    }

//    inline fun <T> with(t: T, body: T.() -> Unit) {
//        t.body()
//    }

    override fun reloadData() {
        p.start()
    }

    fun onSuccess(it: MovieResInfo?) {
        movieResInfo = it
        adapter!!.clear()
        showContent()
        val typeList = it!!.list
        for (info in typeList!!) {
            if (info.showType == "banner") {
                val bannerList = info.childList
                bannerList!!.filter { it.loadType != "video" }
                        .forEach { bannerList.remove(it) }
                val bannerAdapter = SingleViewPagerAdapter<MovieInfo>(this, bannerList, R.layout.item_banner_image_movie)
                bannerAdapter.setPresenter(p)
                adapter!!.add(0, BannerAdapterInfo(bannerAdapter), 0)
            } else if (info.showType == "IN") {
                adapter!!.add(TitleInfo(info.title!!), 1)
                val movieList = info.childList
                adapter!!.addAll(movieList, 2)
            }
        }
    }
}