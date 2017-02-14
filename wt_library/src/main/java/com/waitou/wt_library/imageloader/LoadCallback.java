package com.waitou.wt_library.imageloader;

import android.graphics.Bitmap;

/**
 * Created by wanglei on 2016/12/21.
 */

public interface  LoadCallback {
    void onLoadFailed(Throwable e);

    void onLoadReady(Bitmap bitmap);

}
