package com.waitou.towards.net.cache;

import com.blankj.utilcode.util.Utils;
import com.waitou.towards.bean.GankResultsInfo;
import com.waitou.towards.bean.GankResultsTypeInfo;
import com.waitou.towards.net.DataLoader;

import java.util.List;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.DynamicKeyGroup;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * Created by waitou on 17/3/8.
 */

public class Repository {

    private        CacheProviders mCacheProviders;
    private static Repository     sRepository;

    private Repository() {
        mCacheProviders = new RxCache.Builder()
                .persistence(Utils.getApp().getCacheDir(), new GsonSpeaker())
                .using(CacheProviders.class);
    }

    public static Repository getRepository() {
        if (sRepository == null) {
            synchronized (Repository.class) {
                if (sRepository == null) {
                    sRepository = new Repository();
                }
            }
        }
        return sRepository;
    }

    /**
     * 首页推荐缓存数据
     */
    public Observable<Reply<GankResultsInfo>> getGankIoDay(String year, String month, String day, boolean isUpDate) {
        return mCacheProviders.getGankIoDay(DataLoader.getGankApi().getGankIoDay(year, month, day).map(dayInfo -> {
            if (dayInfo.category == null || dayInfo.category.size() == 0) {
                dayInfo.results.isNull = true;
            }
            return dayInfo.results;
        }), new DynamicKey(day), new EvictDynamicKey(isUpDate));
    }


    /**
     * 首页干货数据
     */
    public Observable<Reply<List<GankResultsTypeInfo>>> getGankIoData(String type, int page) {
        return mCacheProviders.getGankIoData(DataLoader.getGankApi().getGankIoData(type, page).map(listGankIoDayInfo -> listGankIoDayInfo.results)
                , new DynamicKeyGroup(type, page));
    }
}
