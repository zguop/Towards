package com.waitou.net_library;

import com.waitou.net_library.http.AsyncOkHttpClient;
import com.waitou.net_library.http.HttpUtil;

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
                .baseUrl(HttpUtil.setCurrentUrl(baseUrl))
                .client(AsyncOkHttpClient.getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T provide(String baseUrl, Class<T> tClass) {
        if (dataServiceMap == null) {
            dataServiceMap = new HashMap<>();
        }

        if (!dataServiceMap.containsKey(baseUrl) || dataServiceMap.get(baseUrl) == null) {
            dataServiceMap.put(baseUrl, getRetrofit(baseUrl).create(tClass));
        }

        return (T) dataServiceMap.get(baseUrl);
    }






    @Deprecated
    public <T> T provide(Class<T> tClass) {
        if (dataServiceMap == null) {
            dataServiceMap = new HashMap<>();
        }
        if (!dataServiceMap.containsKey(tClass.getName()) || dataServiceMap.get(tClass.getName()) == null) {
            dataServiceMap.put(tClass.getName(), getRetrofit(HttpUtil.RANDOM_BASE_URL).create(tClass));
        }
        return (T) dataServiceMap.get(tClass.getName());
    }


    @Deprecated
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
