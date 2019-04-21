package com.waitou.net_library.http;


import com.safframework.http.interceptor.LoggingInterceptor;
import com.waitou.net_library.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by waitou on 17/1/3.
 */

public class AsyncOkHttpClient {

    private static final int TIMEOUT = 20_000;

    private static OkHttpClient sOkHttpClient;

    private AsyncOkHttpClient() {
    }

    public static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            LoggingInterceptor loggingInterceptor = new LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .tag("aa")
                    .requestTag("aa")
                    .responseTag("aa")
                    .request()
                    .response()
                    .logLevel(LoggingInterceptor.LogLevel.ERROR)
                    .hideVerticalLine()// 隐藏竖线边框
                    .build();
            sOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)//日志打印拦截工具
                    .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .build();
        }
        return sOkHttpClient;
    }

}
