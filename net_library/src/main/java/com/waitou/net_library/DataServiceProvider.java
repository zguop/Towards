package com.waitou.net_library;

import com.waitou.net_library.http.AsyncOkHttpClient;
import com.waitou.net_library.http.HttpUtil;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
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


    public <T> T provide(Class<T> tClass) {
        if (dataServiceMap == null) {
            dataServiceMap = new HashMap<>();
        }

        if (!dataServiceMap.containsKey(tClass.getName()) || dataServiceMap.get(tClass.getName()) == null) {
            dataServiceMap.put(tClass.getName(), getRetrofit().create(tClass));
        }

        return (T) dataServiceMap.get(tClass.getName());
    }

    private Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(HttpUtil.getCurrentAddress())
                    .client(AsyncOkHttpClient.getOkHttpClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public void reloadService(String address) {
        if (HttpUtil.getCurrentAddress().contains(address)) {
            return;
        }
        if (dataServiceMap != null) {
            dataServiceMap.clear();
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.setCurrentUrl(address))
                .client(AsyncOkHttpClient.getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
