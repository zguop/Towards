package com.waitou.wt_library.base;

/**
 * Created by waitou on 17/1/9.
 */

public abstract class XPresent<V extends IView> implements UIPresent<V> {

    private V v;

    @Override
    public V getV() {
        if (v == null) {
            throw new IllegalStateException("v can not be null");
        }
        return v;
    }

    @Override
    public void attachV(V v) {
        this.v = v;
    }

    @Override
    public void detachV() {
        v = null;
    }
}
