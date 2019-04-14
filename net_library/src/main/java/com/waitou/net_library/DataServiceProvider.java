package com.waitou.net_library;

import com.waitou.net_library.http.AsyncOkHttpClient;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by waitou on 17/1/3.
 */

public class DataServiceProvider {

    private static DataServiceProvider sDataServiceProvider;

    private HashMap<String, Object> dataServiceMap;
    private Retrofit                retrofit;


    public static DataServiceProvider getInstance() {
        if (sDataServiceProvider == null) {
            synchronized (DataServiceProvider.class) {
                if (sDataServiceProvider == null) {
                    sDataServiceProvider = new DataServiceProvider();
                }
            }
        }
        return sDataServiceProvider;
    }

    private Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(AsyncOkHttpClient.getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @SuppressWarnings("unchecked")
    public <T> T provide(String baseUrl, Class<T> tClass) {
        if (dataServiceMap == null) {
            dataServiceMap = new HashMap<>();
        }

        if (!dataServiceMap.containsKey(baseUrl) || dataServiceMap.get(baseUrl) == null) {
            dataServiceMap.put(baseUrl, getRetrofit(baseUrl).create(tClass));
        }

        return (T) dataServiceMap.get(baseUrl);
    }
}
