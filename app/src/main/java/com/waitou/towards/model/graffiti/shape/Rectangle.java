package com.waitou.towards.model.graffiti.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by waitou on 17/3/19.
 * 矩形
 */

public class Rectangle extends Shape {

    private float startX;
    private float startY;
    private float stopX;
    private float stopY;

    public Rectangle(Paint paint) {
        super(paint);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(startX, startY, stopX, stopY, mPaint);
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
