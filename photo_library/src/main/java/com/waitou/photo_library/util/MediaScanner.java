package com.waitou.photo_library.util;

import android.media.MediaScannerConnection;

import com.waitou.wt_library.BaseApplication;

import io.reactivex.functions.Action;


/**
 * Created by waitou on 17/4/13.
 * 扫描多媒体
 */

public class MediaScanner {
    public static void scan(String path, String type, Action action0)  {
        MediaScannerConnection.scanFile(BaseApplication.getApp(), new String[]{path}, new String[]{type}, (path1, uri) -> {
            try {
                action0.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
