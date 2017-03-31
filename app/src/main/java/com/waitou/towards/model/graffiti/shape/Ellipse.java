package com.waitou.towards.model.graffiti.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * Created by waitou on 17/3/19.
 * 椭圆工具
 */

public class Ellipse extends Shape {

    private float startX;
    private float startY;
    private float strokeWidth;
    private RectF mRectF;

    public Ellipse() {
        mRectF = new RectF();
        strokeWidth = mPaint.getStrokeWidth();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawOval(mRectF, mPaint);
    }

    @Override
    public void move(float x, float y, int actionMasked) {
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mRectF.set(startX - strokeWidth / 2, startY - strokeWidth / 2, x + strokeWidth / 2, y + strokeWidth / 2);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }
}
