package com.to.aboomy.rx_lib;

import android.util.Log;
import android.util.SparseArray;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * auth aboom
 * date 2018/7/7
 */
public class RxComposite {

    private static final CompositeDisposable     TASK_DISPOSABLE = new CompositeDisposable();
    private static final SparseArray<Disposable> TASK_MAP        = new SparseArray<>();

    /**
     * 向队列中添加一个Subscription
     */
    public synchronized static void pend(Disposable disposable) {
        if (disposable != null) {
            TASK_DISPOSABLE.add(disposable);
        }
    }

    public static void clear() {
        TASK_DISPOSABLE.dispose();
    }

    public synchronized static <T> void disposableScribe(Observable<T> observable, Consumer<? super T> onNext) {
        if (observable == null) {
            return;
        }
        int increasingRequestCode = TASK_MAP.size();
        while (TASK_MAP.get(increasingRequestCode) != null) {
            increasingRequestCode++;
        }
        int finalIncreasingRequestCode = increasingRequestCode;
        final Action unSubscribe = () -> {
            if (TASK_MAP.get(finalIncreasingRequestCode) != null) {
                TASK_MAP.get(finalIncreasingRequestCode).dispose();
                TASK_MAP.remove(finalIncreasingRequestCode);
            }
        };
        TASK_MAP.put(finalIncreasingRequestCode, observable
                .doOnComplete(unSubscribe)
                .doOnError(throwable -> unSubscribe.run())
                .subscribe(onNext));
    }


    public synchronized static void singlePend(Disposable disposable) {
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        int increasingRequestCode = TASK_MAP.size();
        Log.e("aa", "singlePend code " + increasingRequestCode);
        while (TASK_MAP.get(increasingRequestCode) != null) {
            TASK_MAP.get(increasingRequestCode).dispose();
            TASK_MAP.remove(increasingRequestCode);
        }
        TASK_MAP.put(increasingRequestCode, disposable);
    }
}
