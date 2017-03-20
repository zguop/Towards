package com.waitou.towards.model.graffiti.shape;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by waitou on 17/3/19.
 * 橡皮
 */

public class Eraser extends Pencil {

    public Eraser(Paint paint) {
        super(paint);
        mPaint.setAlpha(0);
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }
}
