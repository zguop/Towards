package com.waitou.towards.model.graffiti;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by waitou on 17/3/28.
 * 图片操作层
 */

public class GraffitiPicView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;
    private Canvas        mCanvas;

    public GraffitiPicView(Context context) {
        this(context, null);
    }

    public GraffitiPicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

    }

    public void draw() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.WHITE);
//            mCanvas.drawBitmap(UImage.getBitmap(getResources(), R.drawable.logo, UDimens.getDeviceWidth(getContext()), UDimens.getDeviceHeight(getContext())), 0, 0, null);
        } finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        draw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}