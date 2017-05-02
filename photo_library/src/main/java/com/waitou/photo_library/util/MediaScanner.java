package com.waitou.photo_library.util;

import android.media.MediaScannerConnection;

import com.waitou.wt_library.BaseApplication;

import rx.functions.Action0;

/**
 * Created by waitou on 17/4/13.
 * 扫描多媒体
 */

public class MediaScanner {
    public static void scan(String path, String type, Action0 action0) {
        MediaScannerConnection.scanFile(BaseApplication.getApp(), new String[]{path}, new String[]{type}, (path1, uri) -> action0.call());
    }
}
