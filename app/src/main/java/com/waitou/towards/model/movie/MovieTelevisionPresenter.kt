package com.waitou.towards.model.movie

import android.text.TextUtils
import com.blankj.utilcode.util.ToastUtils
import com.waitou.net_library.helper.RxTransformerHelper
import com.waitou.net_library.helper.SimpleErrorVerify
import com.waitou.towards.net.DataLoader
import com.waitou.wt_library.base.XPresent
import com.waitou.wt_library.recycler.adapter.BaseViewAdapter
import com.waitou.wt_library.view.SingleViewPagerAdapter

/**
 * Created by waitou on 17/5/25.
 */
class MovieTelevisionPresenter : XPresent<MovieRecommendActivity>(), BaseViewAdapter.Presenter, SingleViewPagerAdapter.Presenter {

    override fun start() {
        v.pend(DataLoader.getMovieApi().homeMoviePage
                .compose(RxTransformerHelper.applySchedulersAndAllFilter(object : SimpleErrorVerify() {
                    override fun call(throwable: Throwable?) {
                        v.showError()
                    }
                })).filter {
                    val success = it.code == 200
                    if (!success) {
                        ToastUtils.showShort(if (TextUtils.isEmpty(it.msg)) "服务器开小差" else it.msg)
                    }
                    success
                }
                .map { it.result }
                .subscribe { v.onSuccess(it) })
    }
}

