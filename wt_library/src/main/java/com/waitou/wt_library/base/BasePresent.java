package com.waitou.wt_library.base;

import java.lang.ref.WeakReference;

/**
 * auth aboom
 * date 2019/4/7
 */
public class BasePresent<V extends IView> implements IPresent<V> {

    private WeakReference<V> v;

    @Override
    public void attachV(V view) {
        v = new WeakReference<>(view);
    }

    @Override
    public void detachV() {
        if (v.get() != null) {
            v.clear();
        }
        v = null;
    }

    protected V getV() {
        if (v == null || v.get() == null) {
            throw new IllegalStateException("v can not be null");
        }
        return v.get();
    }

    @Override
    public boolean hasV() {
        return v != null && v.get() != null;
    }
}
