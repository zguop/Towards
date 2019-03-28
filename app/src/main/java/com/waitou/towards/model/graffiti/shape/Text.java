package com.waitou.towards.model.graffiti.shape;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by waitou on 17/3/19.
 * 文字
 */

public class Text extends Shape {

    private float startX;
    private float startY;

    public Text() {
        mPaint.setTextSize(mPaint.getStrokeWidth());
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(text, startX, startY, mPaint);
    }

    @Override
    public void move(float x, float y, int actionMasked) {
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }
}
