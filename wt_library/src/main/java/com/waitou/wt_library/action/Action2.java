package com.waitou.wt_library.action;

/**
 * auth aboom
 * date 2018/11/29
 */
public interface Action2<T, K> {
    void call(T t, K k);
}
