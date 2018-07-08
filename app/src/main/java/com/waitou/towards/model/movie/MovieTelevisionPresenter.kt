package com.waitou.towards.model.movie

import com.waitou.net_library.helper.RxTransformerHelper
import com.waitou.towards.net.DataLoader
import com.waitou.towards.net.SimpleErrorVerify
import com.waitou.wt_library.base.XPresent
import com.waitou.wt_library.recycler.adapter.BaseViewAdapter
import com.waitou.wt_library.view.SingleViewPagerAdapter

/**
 * Created by waitou on 17/5/25.
 */
class MovieTelevisionPresenter : XPresent<MovieRecommendActivity>(), BaseViewAdapter.Presenter, SingleViewPagerAdapter.Presenter {

    override fun start() {
        v.pend(DataLoader.getMovieApi().homeMoviePage
                .compose(RxTransformerHelper.applySchedulersAndAllFilter(v, object : SimpleErrorVerify() {
                    override fun call(code: String?, desc: String?) {
                        super.call(code, desc)
                        v.showError()
                    }
                    override fun netError(throwable: Throwable?) {
                        super.netError(throwable)
                        v.showError()
                    }
                }))
                .map { it.result }
                .subscribe({ v.onSuccess(it) }))
    }
}

