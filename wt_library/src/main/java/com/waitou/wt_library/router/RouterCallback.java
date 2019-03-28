package com.waitou.wt_library.router;

import android.app.Activity;

/**
 * Created by wanglei on 2016/11/29.
 */

public interface RouterCallback {

    void onBefore(Activity from, Class<?> to);

    void OnNext(Activity from, Class<?> to);

    void onError(Activity from, Class<?> to, Throwable throwable);

}
