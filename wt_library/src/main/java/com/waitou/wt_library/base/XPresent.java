package com.waitou.wt_library.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by waitou on 17/1/9.
 */

public abstract class XPresent<V extends UIView> implements UIPresent<V> {

    private V                   v;
    private CompositeDisposable mCompositeDisposable;

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
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        v = null;
    }

    @Override
    public void start() {
    }

    protected void pend(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        if (disposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }
}
