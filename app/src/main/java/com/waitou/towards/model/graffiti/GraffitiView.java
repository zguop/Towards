package com.waitou.towards.model.graffiti;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.waitou.towards.model.graffiti.shape.Perch;
import com.waitou.towards.model.graffiti.shape.Shape;
import com.waitou.towards.model.graffiti.shape.ShapeFactory;
import com.waitou.towards.util.AlertToast;
import com.waitou.wt_library.kit.UDimens;
import com.waitou.wt_library.kit.UImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waitou on 17/3/19.
 * 画板
 */

public class GraffitiView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;
    private ShapeFactory  mFactory;
    private Shape         mShape;
    private Bitmap        mBitmap;

    private boolean isCreate;
    private int     shapeIndex;
    private int     type;
    private int     width;
    private int     color;

    private List<Shape>   mShapes    = new ArrayList<>();
    private List<Integer> mCleanBuff = new ArrayList<>();

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

    /**
     * 核心绘制方法
     */
    private void doDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
        int stamp = 0;
        if (mCleanBuff.size() > 0) {
            stamp = mCleanBuff.get(mCleanBuff.size() - 1);
        }
        for (int i = stamp; i < shapeIndex; i++) {
            mShapes.get(i).draw(canvas);
        }
        if (mShape != null) {
            mShape.draw(canvas);
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
        draw();
    }

    /**
     * 抬起
     */
    private void canvasUp(float x, float y) {
        mShape.move(x, y, MotionEvent.ACTION_MOVE);
        draw();
        addShape(mShape);
        mShape = null;
    }

    /**
     * 前进
     */
    public void redo() {
        if (mShapes.size() > 0) {
            if (shapeIndex >= mShapes.size()) {
                return;
            }
            if (mShapes.get(shapeIndex) instanceof Perch) {
                mCleanBuff.add(shapeIndex);
            }
            shapeIndex++;
            if (isCreate) {
                draw();
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
            if (mShapes.get(shapeIndex) instanceof Perch) {
                mCleanBuff.remove(mCleanBuff.size() - 1);
            }
            if (isCreate) {
                draw();
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
                Perch perch = new Perch(null);
                addShape(perch);
                mCleanBuff.add(shapeIndex);
            }
        }
        if (isCreate) {
            draw();
        }
    }

    /**
     * 检测删除回退前进在开始绘制后没有用的数据
     */
    private void removeRedundantShape() {
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

    /**
     * 保存为图片
     */
    public void save() {
        if (shapeIndex > 0 && !(mShapes.get(mShapes.size() - 1) instanceof Perch) || mBitmap != null) {
            Bitmap bitmap = Bitmap.createBitmap(UDimens.getDeviceWidth(getContext()), UDimens.getDeviceHeight(getContext()), Bitmap.Config.ARGB_8888);
            Canvas bitCanvas = new Canvas(bitmap);
            doDraw(bitCanvas);
            UImage.saveImageToGallery(getContext(), bitmap);
            bitmap.recycle();
            AlertToast.show("图片成功保存到相册O(∩_∩)O~");
        } else {
            AlertToast.show("先绘制点什么吧!╮(╯▽╰)╭");
        }
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
