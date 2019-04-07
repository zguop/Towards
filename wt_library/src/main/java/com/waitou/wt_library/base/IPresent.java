package com.waitou.wt_library.base;

/**
 * auth aboom
 * date 2019/4/7
 */
public interface IPresent<V> {

    void attachV(V view);

    void detachV();

    boolean hasV();
}
