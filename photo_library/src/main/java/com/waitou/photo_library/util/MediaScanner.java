package com.waitou.photo_library.util;

import android.media.MediaScannerConnection;
import android.net.Uri;

import com.waitou.wt_library.BaseApplication;

/**
 * Created by waitou on 17/4/13.
 * 扫描多媒体
 */

public class MediaScanner {

    private static MediaScannerConnection.MediaScannerConnectionClient sClient;
    private static MediaScannerConnection                              sMediaScannerConnection;

    public static void scan(final String path, final String type) {
        if (sClient == null) {
            sClient = new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {
                    sMediaScannerConnection.scanFile(path, type);
                }

                @Override
                public void onScanCompleted(String path, Uri uri) {
                    sMediaScannerConnection.disconnect();
                }
            };
        }
        if (sMediaScannerConnection == null) {
            sMediaScannerConnection = new MediaScannerConnection(BaseApplication.getApp(), sClient);
        }
        sMediaScannerConnection.connect();
    }
}
