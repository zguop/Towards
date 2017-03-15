package com.waitou.towards.net.cache;

import com.waitou.towards.TowardsApplication;
import com.waitou.towards.bean.GankResultsInfo;
import com.waitou.towards.net.DataLoader;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.Reply;
import io.rx_cache.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import rx.Observable;

/**
 * Created by waitou on 17/3/8.
 */

public class Repository {

    private        CacheProviders mCacheProviders;
    private static Repository     sRepository;

    private Repository() {
        mCacheProviders = new RxCache.Builder()
                .persistence(TowardsApplication.getApp().getCacheDir(), new GsonSpeaker())
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
        return mCacheProviders.getGankIoDay(DataLoader.getGankApi().getGankIoDay(year, month, day).map(dayInfo -> dayInfo.results)
                , new DynamicKey(day), new EvictDynamicKey(isUpDate));
    }

}
