package com.waitou.towards.model.graffiti;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.waitou.towards.R;
import com.waitou.wt_library.kit.UDimens;

/**
 * Created by waitou on 17/3/30.
 */

public class GraffitiPicView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;
    private boolean       isCreate;
    private Bitmap        canvasBitmap;
    private Matrix        mMatrix;
    private float         mScale;

    public GraffitiPicView(Context context) {
        this(context, null);
    }

    public GraffitiPicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GraffitiPicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        canvasBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

        mMatrix = new Matrix();
        judgePosition();
//        mMatrix.setRotate(180, canvasBitmap.getWidth() / 2, canvasBitmap.getHeight() / 2);
//        mMatrix.setTranslate(canvasBitmap.getWidth() , canvasBitmap.getHeight() );
    }

    private void judgePosition() {
        int deviceWidth = UDimens.getDeviceWidth();
        int deviceHeight = UDimens.getDeviceHeight();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            deviceHeight -= UDimens.getStatusHeight();
        }

        int width = canvasBitmap.getWidth();
        int height = canvasBitmap.getHeight();
        float transX = 0;
        float transY = 0;
        if (width < deviceWidth) {
            transX = (deviceWidth - width) / 2;
        }

        if (height < deviceHeight) {
            transY = (deviceHeight - height) / 2;
        }
        mMatrix.setTranslate(transX, transY);
    }

    /**
     * 获取SurfaceView画布进行绘制
     */
    private void draw() {
        Canvas canvas = null;
        try {
            canvas = mSurfaceHolder.lockCanvas();
            doDraw(canvas);
        } finally {
            if (canvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void doDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        if (canvasBitmap != null) {
            canvas.drawBitmap(canvasBitmap, mMatrix, null);
        }
    }

    public void setScale(float scale) {
        this.mScale = scale;
        Log.d("aa" , " scale = " + scale);
    }

    public boolean checkSave() {
        return canvasBitmap != null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isCreate = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        draw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isCreate = false;
    }
}
