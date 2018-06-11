package com.to.aboomy.zxing_lib;

import android.hardware.Camera;
import android.util.Log;

/**
 * auth aboom
 * date 2018/5/23
 */
public class CameraScanAnalysis implements Camera.PreviewCallback {

    private boolean allowAnalysis = true;

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.e("aa" , " ===============");
    }

    public void onStop() {
        this.allowAnalysis = false;
    }
}
