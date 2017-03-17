package com.waitou.towards.net.cache;

import com.waitou.towards.bean.GankResultsInfo;
import com.waitou.towards.bean.GankResultsTypeInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rx_cache.DynamicKey;
import io.rx_cache.DynamicKeyGroup;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.LifeCache;
import io.rx_cache.Reply;
import rx.Observable;

/**
 * Created by waitou on 17/3/8.
 */

public interface CacheProviders {

    /**
     * 缓存首页推荐数据
     *
     * @param ioDayInfo       数据源
     * @param dynamicKey      存入天数 标识 第几天
     * @param evictDynamicKey 是否请求新数据源 删除缓存
     */
    Observable<Reply<GankResultsInfo>> getGankIoDay(Observable<GankResultsInfo> ioDayInfo, DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);

    /**
     * 缓存干货数据 50分钟
     *
     * @param info            数据源
     * @param dynamicKeyGroup 需要两个key标识缓存数据 类型 分页
     */
    @LifeCache(duration = 50, timeUnit = TimeUnit.HOURS)
    Observable<Reply<List<GankResultsTypeInfo>>> getGankIoData(Observable<List<GankResultsTypeInfo>> info, DynamicKeyGroup dynamicKeyGroup);
}
