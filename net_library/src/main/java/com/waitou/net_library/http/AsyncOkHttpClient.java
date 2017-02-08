package com.waitou.net_library.http;


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
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            sOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)//日志打印拦截工具
                    .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .build();
        }
        return sOkHttpClient;
    }

}
