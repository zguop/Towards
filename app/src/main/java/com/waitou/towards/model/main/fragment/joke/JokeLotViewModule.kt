package com.waitou.towards.model.main.fragment.joke

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.to.aboomy.rx_lib.RxHelper
import com.waitou.net_library.helper.RxTransformerHelper
import com.waitou.net_library.helper.SimpleErrorVerify
import com.waitou.net_library.model.APIResult
import com.waitou.towards.bean.JokeInfo
import com.waitou.towards.common.Values
import com.waitou.towards.net.DataLoader

/**
 * auth aboom
 * date 2019-05-19
 */
class JokeLotViewModule : ViewModel() {

    private val rx = RxHelper.getHelper()

    val mutableLiveData = MutableLiveData<APIResult<MutableList<JokeInfo>>>()

    fun loadJokeData(type: Int?, page: Int = 1) {
        rx.pend(DataLoader.getJokeApi()
                .getTextJoke(Values.KEY, page, if (type == 1) "pic" else "")
                .doOnNext {
                    if (Integer.valueOf(it.errorCode) != 0) {
                        throw Exception(it.reason)
                    }
                }.map { it.result }
                .compose(RxTransformerHelper.applySchedulersAndAllFilter(object : SimpleErrorVerify() {
                    override fun call(throwable: Throwable?) {
                        super.call(throwable)
                        mutableLiveData.postValue(APIResult.failure(page))
                    }
                }))
                .subscribe {
                    mutableLiveData.postValue(APIResult.success(page, it))
                })
    }

    override fun onCleared() {
        rx.onDestroy()
    }
}
