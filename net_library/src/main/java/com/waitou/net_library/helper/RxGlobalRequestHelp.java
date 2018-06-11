package com.waitou.net_library.helper;

import android.util.Log;
import android.util.SparseArray;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * author   itxp
 * date     2018/5/12 20:27
 * des
 */

public class RxGlobalRequestHelp {

    private static final SparseArray<Disposable> TASK_MAP = new SparseArray<>();

    private volatile static int sIncreasingRequestCode = 0;

    public synchronized static <T> void request(Observable<T> observable, Consumer<? super T> onNext) {
        if (observable == null) {
            return;
        }
        final int taskKey = sIncreasingRequestCode++;
        final Action unSubscribe = () -> {
            Log.e("aa", "RxGlobalRequestHelp remove");
            if (TASK_MAP.get(taskKey) != null) {
                TASK_MAP.get(taskKey).dispose();
                TASK_MAP.remove(taskKey);
            }
        };
        TASK_MAP.put(taskKey, observable
                .doOnComplete(unSubscribe)
                .doOnError(throwable -> unSubscribe.run())
                .subscribe(onNext));
    }
}
