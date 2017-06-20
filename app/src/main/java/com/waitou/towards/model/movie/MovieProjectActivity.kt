package com.waitou.towards.model.movie

import android.os.Bundle
import com.waitou.towards.R
import com.waitou.towards.databinding.ActivityMovieBinding
import com.waitou.wt_library.base.XActivity

/**
 * Created by waitou on 17/6/19.
 * 专题推荐
 */
class MovieProjectActivity : XActivity<MovieProjectPresenter, ActivityMovieBinding>() {

    override fun createPresenter(): MovieProjectPresenter {
        return MovieProjectPresenter()
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_movie
    }

    override fun initData(savedInstanceState: Bundle?) {
        initMenuActionBar("专题推荐")
        reloadData()
    }

    override fun reloadData() {
        p.start()
    }
}
