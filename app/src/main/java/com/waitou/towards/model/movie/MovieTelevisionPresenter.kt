package com.waitou.towards.model.movie


/**
 * Created by waitou on 17/5/25.
 */
//class MovieTelevisionPresenter : XPresent<MovieRecommendActivity>(), BaseViewAdapter.Presenter, SingleViewPagerAdapter.Presenter {
//
//    override fun start() {
//        v.pend(DataLoader.getMovieApi().homeMoviePage
//                .compose(RxTransformerHelper.applySchedulersAndAllFilter(object : SimpleErrorVerify() {
//                    override fun call(throwable: Throwable?) {
//                        v.showError()
//                    }
//                })).filter {
//                    val success = it.code == 200
//                    if (!success) {
//                        ToastUtils.showShort(if (TextUtils.isEmpty(it.msg)) "服务器开小差" else it.msg)
//                    }
//                    success
//                }
//                .map { it.result }
//                .subscribe { v.onSuccess(it) })
//    }
//}

