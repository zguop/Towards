package com.waitou.towards.model.graffiti;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.waitou.towards.model.graffiti.shape.Shape;
import com.waitou.towards.model.graffiti.shape.ShapeFactory;

/**
 * Created by waitou on 17/3/19.
 * 画板
 */

public class GraffitiView extends SurfaceView implements SurfaceHolder.Callback2 {

    private SurfaceHolder mSurfaceHolder;
    private Canvas        mCanvas;

    private boolean      isCreate;
    private ShapeFactory mFactory;
    private Shape        mShape;

    public GraffitiView(Context context) {
        this(context, null);
    }

    public GraffitiView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GraffitiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        //设置背景为全透明
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        //在上方
        setZOrderOnTop(true);
        //设置屏幕保持常亮
        setKeepScreenOn(true);
        //创建绘制画笔
        Paint paint = new Paint();
        //创建形状工厂
        mFactory = new ShapeFactory(paint);
        //默认初始化铅笔工具
        mShape = mFactory.create(0);
    }

    public void draw() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            if (mShape != null) {
                mShape.draw(mCanvas);
            }
        } finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    /**
     * 按下
     */
    private void canvasDown(float x, float y) {
        mShape.move(x, y, MotionEvent.ACTION_DOWN);
    }

    /**
     * 移动
     */
    private void canvasMove(float x, float y) {
        mShape.move(x, y, MotionEvent.ACTION_MOVE);
        draw();
    }

    /**
     * 抬起
     */
    private void canvasUp(float x, float y) {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                canvasDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                canvasMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                canvasUp(x, y);
                break;
        }
        return true;
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {
        Log.d("aa", "surfaceRedrawNeeded ");

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isCreate = true;
        Log.d("aa", "surfaceCreated ");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("aa", "surfaceChanged ");
        draw();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isCreate = false;
        Log.d("aa", "surfaceDestroyed ");
    }
}
