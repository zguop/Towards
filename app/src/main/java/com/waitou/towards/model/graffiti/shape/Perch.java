package com.waitou.towards.model.graffiti.shape;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

/**
 * Created by waitou on 17/3/28.
 * 清屏算一次操作
 */

public class Perch extends Shape {

    public Perch(Paint paint) {
        super(paint);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    @Override
    public void move(float x, float y, int actionMasked) {
    }
}
