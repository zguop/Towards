package com.waitou.towards.model.graffiti.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

/**
 * Created by waitou on 17/3/19.
 * 铅笔工具
 */

public class Pencil extends Shape {

    private Path mPath;

    public Pencil(Paint paint) {
        super(paint);
        mPath = new Path();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void move(float x, float y, int actionMasked) {
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }
}
