package com.waitou.towards.model.graffiti.shape;

import com.waitou.towards.enums.GraffitiToolEnum;

/**
 * Created by waitou on 17/3/19.
 * 图形工具生产工厂
 */

public class ShapeFactory {

    public static Shape create(int type) {
        Shape shape = null;
        GraffitiToolEnum toolEnum = GraffitiToolEnum.valueOf(type);
        switch (toolEnum) {
            case pencil:
                shape = new Pencil();
                break;
            case circle:
                shape = new Circle();
                break;
            case dashedLine:
                shape = new DashedLine();
                break;
            case ellipse:
                shape = new Ellipse();
                break;
            case eraser:
                shape = new Eraser();
                break;
            case line:
                shape = new Line();
                break;
            case rectangle:
                shape = new Rectangle();
                break;
            case text:
                shape = new Text();
                break;
            case triangle:
                shape = new Triangle();
                break;
            case arrow:
                shape = new Arrow();
                break;
            case biarrow:
                shape = new BiArrow();
                break;
        }
        return shape;
    }
}
