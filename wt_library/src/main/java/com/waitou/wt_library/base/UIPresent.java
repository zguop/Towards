package com.waitou.wt_library.base;

/**
 * Created by waitou on 17/1/9.
 */

public interface UIPresent<V extends UIView> {

    void start();

    V getV();

    void attachV(V v);

    void detachV();
}
