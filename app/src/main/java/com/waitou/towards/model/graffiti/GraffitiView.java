package com.waitou.towards.model.graffiti;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.waitou.towards.R;
import com.waitou.towards.model.graffiti.shape.Perch;
import com.waitou.towards.model.graffiti.shape.Shape;
import com.waitou.towards.model.graffiti.shape.ShapeFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waitou on 17/3/19.
 * 画板
 */

public class GraffitiView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;
    private Canvas        mCanvas;

    private boolean      isCreate;
    private ShapeFactory mFactory;
    private Shape        mShape;
    private List<Shape> mShapes = new ArrayList<>();
    private int shapeIndex;

    private int    type;
    private int    width;
    private int    color;
    private Bitmap mBitmap;

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
        //设置屏幕保持常亮
        setKeepScreenOn(true);
        //创建形状工厂
        mFactory = new ShapeFactory();
    }

    public void setShape(int type) {
        this.type = type;
    }

    public void setStrokeWidth(int width) {
        this.width = width;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private void createTool() {
        mShape = mFactory.create(type);
        mShape.setStrokeWidth(width);
        mShape.setPaintColor(color);
    }

    public void draw(boolean isCanvasShape) {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.WHITE);
            if (mBitmap != null) {
                mCanvas.drawBitmap(mBitmap, 0, 0, null);
            }
            for (int i = 0; i < shapeIndex; i++) {
                mShapes.get(i).draw(mCanvas);
            }
            if (isCanvasShape && mShape != null) {
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
        removeRedundantShape();
        createTool();
        mShape.move(x, y, MotionEvent.ACTION_DOWN);
    }

    /**
     * 移动
     */
    private void canvasMove(float x, float y) {
        mShape.move(x, y, MotionEvent.ACTION_MOVE);
        draw(true);
    }

    /**
     * 抬起
     */
    private void canvasUp(float x, float y) {
        mShape.move(x, y, MotionEvent.ACTION_MOVE);
        draw(true);
        addShape(mShape);
        Log.d("aa", " shapeIndex = " + shapeIndex + " canvasUp size =  " + mShapes.size());
    }

    /**
     * 前进
     */
    public void redo() {
        if (mShapes.size() > 0) {
            if (shapeIndex >= mShapes.size()) {
                return;
            }
            shapeIndex++;
            if (isCreate) {
                draw(false);
            }
        }
    }

    /**
     * 回退 控制 shapeIndex 角标
     */
    public void undo() {
        if (mShapes.size() > 0) {
            if (shapeIndex == 0) {
                return;
            }
            shapeIndex--;
            if (isCreate) {
                draw(false);
            }
        }
    }

    /**
     * 清屏
     */
    public void clean() {
        removeRedundantShape();
        if (mShapes.size() > 0) {
            Shape shape = mShapes.get(mShapes.size() - 1);
            if (!(shape instanceof Perch)) {
                Perch perch = new Perch(null, mBitmap);
                addShape(perch);
            }
        }
        if (isCreate) {
            draw(false);
        }
    }


    public void insertBitmap() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        draw(false);
    }

    /**
     * 检测删除回退前进在开始绘制后没有用的数据
     */
    private void removeRedundantShape() {
        Log.d("aa", " removeRedundantShape shapeIndex = " + shapeIndex + " size = " + mShapes.size());
        if (shapeIndex != mShapes.size()) {
            mShapes = mShapes.subList(0, shapeIndex);
        }
    }

    /**
     * 添加笔数
     */
    private void addShape(Shape shape) {
        mShapes.add(shape);
        shapeIndex++;
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
    public void surfaceCreated(SurfaceHolder holder) {
        isCreate = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        draw(false);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isCreate = false;
    }
}
