package com.waitou.towards.model.graffiti;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.waitou.wt_library.kit.UDimens;

/**
 * Created by waitou on 17/3/30.
 * 图片层
 */

public class GraffitiPicView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;
    private boolean       isCreate;
    private Bitmap        canvasBitmap;
    private Matrix        mMatrix;
    private float         scale; //缩放的倍数
    private int           rotate; //旋转的角度
    private float         transX;
    private float         transY;

    private float bitmapWidth;
    private float bitmapHeight;
    private float scaleWidth;
    private float scaleHeight;
    private int   mDeviceWidth;
    private int   mDeviceHeight;

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
        mDeviceWidth = UDimens.getDeviceWidth();
        mDeviceHeight = UDimens.getDeviceHeight();
        mMatrix = new Matrix();
    }

    /**
     * 核心matrix转换方法
     */
    private void resizeMatrix() {
        mMatrix.reset();
        mMatrix.setTranslate(transX, transY);
        mMatrix.postScale(scale, scale);
        mMatrix.preRotate(rotate, bitmapWidth / 2, bitmapHeight / 2);
        if (isCreate) {
            draw();
        }
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

    public void setBitmap(Bitmap bitmap) {
        canvasBitmap = bitmap;
        if (canvasBitmap != null) {
            bitmapWidth = canvasBitmap.getWidth();
            bitmapHeight = canvasBitmap.getHeight();
            resizeMatrix();
        }
    }

    public void setScale(float scale) {
        this.scale = scale;
        scaleWidth = bitmapWidth * this.scale;
        scaleHeight = bitmapHeight * this.scale;
        resizeMatrix();
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
        resizeMatrix();
    }

    public void setTransX(int transX) {
        this.transX = transX;
        resizeMatrix();
    }

    public void setTransY(int transY) {
        this.transY = transY;
        resizeMatrix();
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
        resizeMatrix();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isCreate = false;
    }
}
