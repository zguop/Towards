package com.to.aboomy.rx_lib;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * auth aboom
 * date 2018/8/8
 */
public class RxHelper {

    private CompositeDisposable mCompositeDisposable;
    private Disposable          mDisposable;

    private RxHelper() {
    }

    public static RxHelper getHelper() {
        return new RxHelper();
    }

    /**
     * 向队列中添加一个Disposable
     */
    public void pend(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        if (disposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    /**
     * 只能注册一个的subscription
     */
    public void singlePend(Disposable disposable) {
        if (disposable != null) {
            cancelSinglePend();
            mDisposable = disposable;
        }
    }

    public void cancelPends() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }

    public void cancelSinglePend() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    public void onDestroy() {
        cancelSinglePend();
        cancelPends();
    }
}
