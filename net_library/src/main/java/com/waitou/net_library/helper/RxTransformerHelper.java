package com.waitou.net_library.helper;

import android.content.Context;
import android.util.Log;

import com.waitou.net_library.log.LogUtil;
import com.waitou.net_library.model.BaseResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by waitou on 17/1/4.
 * Retrofit合并RxJava帮助类
 */

public class RxTransformerHelper {

    /**
     * 过滤器，result业务过滤 返回数据源
     */
    public static <T> Observable.Transformer<BaseResponse<T>, T> applySchedulersResult(Context context, ErrorVerify errorVerify) {
        return observable -> observable
                .compose(applySchedulersAndAllFilter(context, errorVerify))
                .map(tBaseResponse -> tBaseResponse.result);
    }

    /**
     * 过滤器， 业务过滤
     */
    public static <T> Observable.Transformer<T, T> applySchedulersAndAllFilter(Context context, ErrorVerify errorVerify) {
        return tObservable -> tObservable
                .compose(applySchedulers())
                .onErrorReturn(throwable -> {
                    //rx异常先处理
                    LogUtil.e(Log.getStackTraceString(throwable));
                    throwable.printStackTrace();
                    if (errorVerify != null) {
                        errorVerify.netError(throwable);
                    }
                    //返回值null 继续执行 filter
                    return null;
                })
                .filter(verifyNotEmpty()) //异常后返回null，filter判断数据中断
                .filter(verifyBusiness(errorVerify)); //业务异常 code异常中断
    }

    /**
     * 优先使用这个，可以继续使用操作符 线程转换
     */
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 数据不为null
     */
    public static <T> Func1<T, Boolean> verifyNotEmpty() {
        return t -> t != null;
    }

    /**
     * 错误码返回  //由于本应用接口都是网络寻找，数据结构不稳定 默认返回成功 自行判断
     */
    public static <T> Func1<T, Boolean> verifyBusiness(ErrorVerify errorVerify) {
        return t -> {
            if (t instanceof BaseResponse) {
                BaseResponse baseResponse = (BaseResponse) t;
                boolean success = Integer.valueOf(baseResponse.errorCode) == 0;
                if (!success && errorVerify != null) {
                    LogUtil.e("返回错误码：" + baseResponse.errorCode + "\t\t\t错误信息：" + baseResponse.reason);
                    errorVerify.call(baseResponse.errorCode, baseResponse.reason);
                }
                return success;
            }
            return true;
        };
    }
}
