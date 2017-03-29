package com.waitou.towards.model.graffiti.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by waitou on 17/3/28.
 * 清屏算一次操作
 */

public class Perch extends Shape {

    private Bitmap drawBitmap;

    public Perch(Paint paint, Bitmap bitmap) {
        super(paint);
        this.drawBitmap = bitmap;
    }

    @Override
    public void draw(Canvas canvas) {
        if (drawBitmap != null) {
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(drawBitmap, 0, 0, null);
        } else {
            canvas.drawColor(Color.WHITE);
        }
    }

    @Override
    public void move(float x, float y, int actionMasked) {
    }
}
