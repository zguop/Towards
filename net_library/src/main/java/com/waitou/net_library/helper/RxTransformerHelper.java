package com.waitou.net_library.helper;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
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
        return observable -> observable
                .compose(applySchedulers())
                .compose(applySchedulersVerify(errorVerify));
    }

    /**
     * 优先使用这个，可以继续使用操作符 线程转换
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 错误异常处理回调
     * onErrorResumeNext 在这个操作符之前发生的异常会调用到这里
     */
    public static <T> ObservableTransformer<T, T> applySchedulersVerify(ErrorVerify errorVerify) {
        return observable -> observable.onErrorResumeNext(throwable -> {
            LogUtils.e(Log.getStackTraceString(throwable));
            throwable.printStackTrace();
            if (errorVerify != null) {
                errorVerify.call(throwable);
            }
            return Observer::onComplete;
        });
    }


    public static <T> Consumer<T> verifyNullThrowable() {
        return t -> {
            if (t == null) {
                throw new Exception("data is empty");
            }
        };
    }
}
