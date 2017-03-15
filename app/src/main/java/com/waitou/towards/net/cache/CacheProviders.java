package com.waitou.towards.net.cache;

import com.waitou.towards.bean.GankResultsInfo;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.Reply;
import rx.Observable;

/**
 * Created by waitou on 17/3/8.
 */

public interface CacheProviders {

    //@LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    //缓存时间

    /**
     * 缓存首页推荐数据
     *
     * @param ioDayInfo       数据源
     * @param dynamicKey      存入天数 标识 第几天
     * @param evictDynamicKey 是否请求新数据源 删除缓存
     */
    Observable<Reply<GankResultsInfo>> getGankIoDay(Observable<GankResultsInfo> ioDayInfo, DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);
}
