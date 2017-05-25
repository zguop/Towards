package com.waitou.towards.model.movie

import android.os.Bundle
import com.waitou.towards.R
import com.waitou.towards.databinding.ActivityMovieRecommendBinding
import com.waitou.wt_library.base.XActivity
import com.waitou.wt_library.kit.AlertToast

/**
 * Created by waitou on 17/5/25.
 * 影视推荐
 */
class MovieRecommendActivity : XActivity<MovieRecommendPresenter, ActivityMovieRecommendBinding>() {

    override fun createPresenter(): MovieRecommendPresenter {
        return MovieRecommendPresenter()
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_movie_recommend
    }

    override fun initData(savedInstanceState: Bundle?) {
        initMenuActionBar("影视精选", "专题", { AlertToast.show("专题") })
    }

    override fun reloadData() {

    }
}