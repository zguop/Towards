package com.waitou.towards.model.graffiti.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by waitou on 17/3/19.
 * 三角形
 */

public class Triangle extends Shape {

    private float startX;
    private float startY;
    private float stopX;
    private float stopY;

    public Triangle(Paint paint) {
        super(paint);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(startX,startY,stopX,stopY,mPaint);
        canvas.drawLine(stopX,stopY,startX,stopY,mPaint);
        canvas.drawLine(startX,stopY,startX,startY,mPaint);
    }

    @Override
    public void move(float x, float y, int actionMasked) {
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                stopX = x;
                stopY = y;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }
}
