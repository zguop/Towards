package com.waitou.net_library.helper;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by waitou on 17/1/4.
 * Retrofit合并RxJava帮助类
 */

public class RxTransformerHelper {

    /**
     * 过滤器， 业务过滤
     */
    public static <T> ObservableTransformer<T, T> applySchedulersAndAllFilter(ErrorVerify errorVerify) {
        return upstream -> upstream
                .compose(applySchedulers())
                .onErrorResumeNext(throwable -> {
                    //rx异常先处理
                    LogUtils.e(Log.getStackTraceString(throwable));
                    throwable.printStackTrace();
                    if (errorVerify != null) {
                        errorVerify.call(throwable);
                    }
                    return Observable.empty();
                });
    }

    /**
     * 优先使用这个，可以继续使用操作符 线程转换
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
