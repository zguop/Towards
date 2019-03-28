package com.waitou.towards.model.graffiti.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by waitou on 17/3/19.
 * 绘图基类
 */

public abstract class Shape {

    Paint  mPaint;
    String text;

    public Shape() {
        mPaint = new Paint();
        mPaint.setDither(true); //设置防抖动 绘制出来比较柔和
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE); //设置画笔为空心
        mPaint.setStrokeJoin(Paint.Join.ROUND); // 设置画笔圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND);   //设置画笔转弯连接处的风格
    }

    /**
     * 设置画笔宽度
     */
    public void setStrokeWidth(int width) {
        if (this instanceof Eraser) {
            width = width * 3;
        }
        if (mPaint != null) mPaint.setStrokeWidth(width);
    }

    /**
     * 设置画笔颜色
     */
    public void setPaintColor(int color) {
        if (mPaint != null) mPaint.setColor(color);
    }

    public abstract void draw(Canvas canvas);

    public abstract void move(float x, float y, int actionMasked);
}
