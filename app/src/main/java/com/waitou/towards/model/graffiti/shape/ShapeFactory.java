package com.waitou.towards.model.graffiti.shape;

import android.graphics.Paint;

import com.waitou.towards.enums.GraffitiToolEnum;

/**
 * Created by waitou on 17/3/19.
 * 图形工具生产工厂
 */

public class ShapeFactory {

    private Paint mShapePaint; //工具笔
    private Paint mEraserPaint;//橡皮擦
    private Paint mDashedPaint;//虚线笔

    public ShapeFactory() {
        mShapePaint = new Paint();
        mEraserPaint = new Paint();
        mDashedPaint = new Paint();
    }

    public Shape create(int type) {
        Shape shape = null;
        GraffitiToolEnum toolEnum = GraffitiToolEnum.valueOf(type);
        switch (toolEnum) {
            case pencil:
                shape = new Pencil(mShapePaint);
                break;
            case circle:
                shape = new Circle(mShapePaint);
                break;
            case dashedLine:
                shape = new DashedLine(mDashedPaint);
                break;
            case ellipse:
                shape = new Ellipse(mShapePaint);
                break;
            case eraser:
                shape = new Eraser(mEraserPaint);
                break;
            case line:
                shape = new Line(mShapePaint);
                break;
            case rectangle:
                shape = new Rectangle(mShapePaint);
                break;
            case text:
                shape = new Text(mShapePaint);
                break;
            case triangle:
                shape = new Triangle(mShapePaint);
                break;
            case arrow:
                shape = new Arrow(mShapePaint);
                break;
            case biarrow:
                shape = new BiArrow(mShapePaint);
                break;
        }
        return shape;
    }
}
