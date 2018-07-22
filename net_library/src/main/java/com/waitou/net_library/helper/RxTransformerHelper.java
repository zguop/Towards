package com.waitou.net_library.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.waitou.net_library.log.LogUtil;
import com.waitou.net_library.model.BaseResponse;
import com.waitou.net_library.model.MovieBaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by waitou on 17/1/4.
 * Retrofit合并RxJava帮助类
 */

public class RxTransformerHelper {

    /**
     * 过滤器，result业务过滤 返回数据源
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> applySchedulersResult(Context context, ErrorVerify errorVerify) {
        return observable -> observable
                .compose(applySchedulersAndAllFilter(context, errorVerify))
                .map(tBaseResponse -> tBaseResponse.result);
    }

    /**
     * 过滤器， 业务过滤
     */
    public static <T> ObservableTransformer<T, T> applySchedulersAndAllFilter(Context context, ErrorVerify errorVerify) {
        return upstream -> upstream
                .compose(applySchedulers())
                .onErrorResumeNext(throwable -> {
                    //rx异常先处理
                    LogUtil.e(Log.getStackTraceString(throwable));
                    throwable.printStackTrace();
                    if (errorVerify != null) {
                        errorVerify.netError(throwable);
                    }
                    //返回值null 继续执行 filter
                    return Observable.empty();
                })
                .filter(verifyNotEmpty()) //异常后返回null，filter判断数据中断
                .filter(verifyBusiness(errorVerify)); //业务异常 code异常中断
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
     * 数据不为null
     */
    public static <T> Predicate<T> verifyNotEmpty() {
        return t -> t != null;
    }

    /**
     * 错误码返回  //由于本应用接口都是网络寻找，数据结构不稳定 默认返回成功 自行判断
     */
    public static <T> Predicate<T> verifyBusiness(ErrorVerify errorVerify) {
        return t -> {
            if (t instanceof BaseResponse) {
                BaseResponse baseResponse = (BaseResponse) t;
                boolean success = Integer.valueOf(baseResponse.errorCode) == 0;
                if (!success && errorVerify != null) {
                    LogUtil.e("返回错误码：" + baseResponse.errorCode + "\t\t\t错误信息：" + baseResponse.reason);
                    errorVerify.call(baseResponse.errorCode, baseResponse.reason);
                }
                return success;
            } else if (t instanceof MovieBaseResponse) {
                MovieBaseResponse movieBaseResponse = (MovieBaseResponse) t;
                boolean success = movieBaseResponse.code == 200;
                if (!success && errorVerify != null) {
                    errorVerify.call(movieBaseResponse.code + "", TextUtils.isEmpty(movieBaseResponse.msg) ? "服务器开小差" : movieBaseResponse.msg);
                }
                return success;
            }
            return true;
        };
    }
}
