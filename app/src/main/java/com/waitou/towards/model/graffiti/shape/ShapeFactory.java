package com.waitou.towards.model.graffiti.shape;

import android.graphics.Paint;
import android.util.SparseArray;

/**
 * Created by waitou on 17/3/19.
 * 图形工具生产工厂
 */

public class ShapeFactory {

    private SparseArray<Shape> sSparseArray = new SparseArray<>();

    private Paint mPaint;

    public ShapeFactory(Paint paint) {
        this.mPaint = paint;
    }

    public Shape create(int type) {
        Shape shape = sSparseArray.get(type);
        if (shape != null) {
            return shape;
        }
        switch (type) {
            case 0:
                shape = new Pencil(mPaint);
                break;
            case 1:
                shape = new Circle(mPaint);
                break;
            case 2:
                shape = new DashedLine(mPaint);
                break;
            case 3:
                shape = new Ellipse(mPaint);
                break;
            case 4:
                shape = new Eraser(mPaint);
                break;
            case 5:
                shape = new Line(mPaint);
                break;
            case 6:
                shape = new Rectangle(mPaint);
                break;
            case 7:
                shape = new Text(mPaint);
                break;
            case 8:
                shape = new Triangle(mPaint);
                break;
            case 9:
                shape = new Arrow(mPaint);
                break;
            case 10:
                shape = new BiArrow(mPaint);
                break;
        }
        sSparseArray.put(type, shape);
        return shape;
    }
}
