package com.waitou.towards.enums;

import com.waitou.towards.R;

/**
 * Created by waitou on 17/3/27.
 */

public enum GraffitiToolEnum {

    pencil(0, R.drawable.svg_ic_line, "涂鸦"),//笔
    eraser(1, R.drawable.svg_ic_line, "橡皮擦"),//橡皮
    text(2, R.drawable.svg_ic_line, "文字"),//文本
    line(3, R.drawable.svg_ic_line, "直线"),
    rectangle(4, R.drawable.svg_ic_line, "矩形"),//矩形
    circle(5, R.drawable.svg_ic_line, "圆形"), //圆
    ellipse(6, R.drawable.svg_ic_line, "椭圆"),//椭圆
    triangle(7, R.drawable.svg_ic_line, "三角形"),//三角
    arrow(8, R.drawable.svg_ic_line, "单向箭头"),//单向箭头
    biarrow(9, R.drawable.svg_ic_line, "双向箭头"),//双向箭头
    dashedLine(10, R.drawable.svg_ic_line, "虚线"),;//虚直线

    private int    type;
    private int    redId;
    private String tool;

    GraffitiToolEnum(int type, int resId, String tool) {
        this.redId = resId;
        this.tool = tool;
        this.type = type;
    }

    public int getRedId() {
        return redId;
    }

    public String getTool() {
        return tool;
    }

    public int getType() {
        return type;
    }


    public static GraffitiToolEnum valueOf(int type) {
        switch (type) {
            case 0:
                return pencil;
            case 1:
                return eraser;
            case 2:
                return text;
            case 3:
                return line;
            case 4:
                return rectangle;
            case 5:
                return circle;
            case 6:
                return ellipse;
            case 7:
                return triangle;
            case 8:
                return arrow;
            case 9:
                return biarrow;
            case 10:
                return dashedLine;
            default:
                return pencil;
        }
    }
}
