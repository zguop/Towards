package com.to.aboomy.rx_lib;

import android.util.SparseArray;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 不依赖某个activity的生命周期的网络请求.请求获得结果后自动unsubscribe
 * auth aboom
 * date 2018/7/7
 */
public class RxComposite {

    private static final SparseArray<Disposable> TASK_MAP = new SparseArray<>();

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


//    public synchronized static void singlePend(Disposable disposable) {
//        if (disposable == null || disposable.isDisposed()) {
//            return;
//        }
//        int increasingRequestCode = TASK_MAP.size();
//        Log.e("aa", "singlePend code " + increasingRequestCode);
//        while (TASK_MAP.get(increasingRequestCode) != null) {
//            TASK_MAP.get(increasingRequestCode).dispose();
//            TASK_MAP.remove(increasingRequestCode);
//        }
//        TASK_MAP.put(increasingRequestCode, disposable);
//    }
}
