package com.waitou.towards.model.graffiti.shape;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by waitou on 17/3/19.
 * 绘制圆
 */

public class Circle extends Shape {

    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    private float mRadius;

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle((startX + stopX) / 2, (startY + stopY) / 2, mRadius, mPaint);
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
                mRadius = (float) ((Math.sqrt((stopX - startX) * (stopX - startX) + (stopY - startY) * (stopY - startY))) / 2);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }
}
