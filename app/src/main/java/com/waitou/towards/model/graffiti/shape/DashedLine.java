package com.waitou.towards.model.graffiti.shape;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.PathEffect;
import android.view.MotionEvent;

/**
 * Created by waitou on 17/3/19.
 */

public class DashedLine extends Shape {

    private float startX;
    private float startY;
    private float stopX;
    private float stopY;

    public DashedLine() {
        PathEffect effects = new DashPathEffect(new float[]{5, 20}, 2);
        mPaint.setPathEffect(effects);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(startX, startY, stopX, stopY, mPaint);
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
