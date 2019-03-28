package com.waitou.towards.model.movie

import android.os.Bundle
import com.to.aboomy.utils_lib.UString
import com.waitou.net_library.model.Displayable
import com.waitou.towards.R
import com.waitou.towards.bean.MovieResInfo
import com.waitou.towards.common.ExtraValue
import com.waitou.towards.databinding.ActivityMovieBinding
import com.waitou.wt_library.base.XActivity
import com.waitou.wt_library.recycler.LayoutManagerUtil
import com.waitou.wt_library.recycler.adapter.SingleTypeAdapter

/**
 * Created by waitou on 17/6/19.
 * 专题推荐
 */
class MovieProjectActivity : XActivity<MovieProjectPresenter, ActivityMovieBinding>() {


    private var adapter: SingleTypeAdapter<Displayable>? = null
    private var movieResInfo: MovieResInfo? = null

    override fun createPresenter(): MovieProjectPresenter {
        return MovieProjectPresenter()
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_movie
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        bar?.initializeHeader("专题推荐")
        movieResInfo = intent.getSerializableExtra(ExtraValue.MOVIE_PROJECT) as MovieResInfo?
        adapter = SingleTypeAdapter(this, R.layout.item_project_movie)
        binding.manager = LayoutManagerUtil.getGridLayoutManager(this, 2)!!
        binding.adapter = adapter
        reloadData()
    }


    override fun reloadData() {
        p.start()
        val typeList = movieResInfo!!.list
        val infoList = typeList!!
                .filter { UString.isNotEmpty(it.moreURL) && UString.isNotEmpty(it.title) }
                .map {
                    val movieInfo = it.childList!![0]
                    movieInfo.title = it.title
                    movieInfo
                }
        adapter!!.set(infoList)

    }
}
