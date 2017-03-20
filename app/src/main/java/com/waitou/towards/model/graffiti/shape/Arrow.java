package com.waitou.towards.model.graffiti.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by waitou on 17/3/19.
 * 单向箭头
 */

public class Arrow extends Shape {

    private float startX;
    private float startY;
    private float stopX;
    private float stopY;

    public Arrow(Paint paint) {
        super(paint);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(startX, startY, stopX, stopY, mPaint);
        drawAL(startX, startY, stopX, stopY, canvas);
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

    private void drawAL(float sx, float sy, float ex, float ey, Canvas canvas) {
        double H = 28; // 箭头高度
        double L = 13.5; // 底边的一半
        double awrad = Math.atan(L / H); // 箭头角度。0.46
        double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
        double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
        double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
        float x_3 = (float) (ex - arrXY_1[0]); // (x3,y3)是第一端点
        float y_3 = (float) (ey - arrXY_1[1]);
        float x_4 = (float) (ex - arrXY_2[0]); // (x4,y4)是第二端点
        float y_4 = (float) (ey - arrXY_2[1]);
        canvas.drawLine(sx, sy, ex, ey, mPaint);
        canvas.drawLine(ex, ey, x_3, y_3, mPaint);
        canvas.drawLine(ex, ey, x_4, y_4, mPaint);
    }

    private double[] rotateVec(float px, float py, double ang, boolean isChLen, double newLen) {
        double mathstr[] = new double[2];
        double vx = px * Math.cos(ang) - py * Math.sin(ang);
        double vy = px * Math.sin(ang) + py * Math.cos(ang);
        if (isChLen) {
            double d = Math.sqrt(vx * vx + vy * vy);
            vx = vx / d * newLen;
            vy = vy / d * newLen;
            mathstr[0] = vx;
            mathstr[1] = vy;
        }
        return mathstr;
    }
}
