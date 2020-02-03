package com.waitou.widget_lib.shape;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

/**
 * auth aboom
 * date 2018/8/10
 */
public class Shape {

    /**
     * 样式
     */
    private int shapeModel;

    /**
     * 背景
     */
    private int solid;

    /**
     * 是否填充
     */
    private boolean isSolid;

    /**
     * 边框线宽度
     */
    private int strokeWidth;

    /**
     * 是否描边
     */
    private boolean isStroke;

    /**
     * 边框线颜色
     */
    private int strokeColor;

    /**
     * 虚线宽度
     */
    private int strokeDashWidth;

    /**
     * 虚线间隔
     */
    private int strokedDashGap;

    /**
     * 是否设置大小
     */
    private boolean isSize;

    /**
     * 宽度
     */
    private int width;

    /**
     * 高度
     */
    private int height;


    /**
     * 是否渐变
     */
    private boolean isGradient;

    /**
     * 渐变颜色数组
     */
    private int[] gradientColors;

    /**
     * 辐射渐变半径
     */
    private float gradientRadius;

    /**
     * 渐变类型 默认线性
     */
    private int gradientType = GradientDrawable.LINEAR_GRADIENT;


    /**
     * 渐变方向 默认从上到下
     */
    private GradientDrawable.Orientation gradientOrientation = GradientDrawable.Orientation.TOP_BOTTOM;

    /**
     * 是否设置圆角
     */
    private boolean isCorners;

    /**
     * 左下角圆角半径
     */
    private float bottomLeftRadius;

    /**
     * 右下角圆角半径
     */
    private float bottomRightRadius;

    /**
     * 左上角圆角半径
     */
    private float topLeftRadius;

    /**
     * 右上角圆角半径
     */
    private float topRightRadius;

    private Shape(int shapeModel) {
        this.shapeModel = shapeModel;
    }


    /**
     * GradientDrawable.RECTANGLE
     * GradientDrawable.OVAL
     * GradientDrawable.LINE
     * GradientDrawable.RING
     */
    public static Shape getShapeRectangle() {
        return new Shape(GradientDrawable.RECTANGLE);
    }

    public static Shape getShapeOval() {
        return new Shape(GradientDrawable.OVAL);
    }

    public static Shape getShapeLine() {
        return new Shape(GradientDrawable.LINE);
    }

    public static Shape getShapeRing() {
        return new Shape(GradientDrawable.RING);
    }

    /**
     * 设置背景色
     *
     * @param color 颜色值
     */
    public Shape setSolid(int color) {
        isSolid = Boolean.TRUE;
        this.solid = color;
        return this;
    }

    /**
     * 设置背景色
     *
     * @param color #FFFFF
     */
    public Shape setSolid(String color) {
        return setSolid(Color.parseColor(color));
    }

    /**
     * 设置边框
     *
     * @param width 边框宽度
     * @param color 边框颜色
     */
    public Shape setStroke(int width, int color) {
        return setStroke(width, color, this.strokeDashWidth, this.strokedDashGap);
    }

    /**
     * 设置边框
     *
     * @param width 边框宽度
     * @param color 边框颜色 #FFFFFF
     */
    public Shape setStroke(int width, String color) {
        return setStroke(width, Color.parseColor(color), this.strokeDashWidth, this.strokedDashGap);
    }

    /**
     * 设置边框虚线
     *
     * @param width     边框宽度
     * @param color     边框颜色
     * @param dashWidth 虚线宽度
     * @param dashGap   虚线间隔宽度
     */
    public Shape setStroke(int width, int color, int dashWidth, int dashGap) {
        this.isStroke = Boolean.TRUE;
        this.strokeWidth = width;
        this.strokeColor = color;
        this.strokeDashWidth = dashWidth;
        this.strokedDashGap = dashGap;
        return this;
    }

    public Shape setSize(int width, int height) {
        this.isSize = Boolean.TRUE;
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * 设置渐变颜色
     * 默认方向重上到下 GradientDrawable.Orientation.TOP_BOTTOM
     * 默认渐变类型 linear
     *
     * @param gradientColors 颜色书序 开始颜色--->结束颜色 至少两个参数
     */
    public Shape setGradient(int... gradientColors) {
        this.isGradient = Boolean.TRUE;
        if (gradientColors.length > 1) {
            this.gradientColors = new int[gradientColors.length];
            System.arraycopy(gradientColors, 0, this.gradientColors, 0, gradientColors.length);
        }
        return this;
    }

    /***
     * 设置渐变方向 默认TOP_BOTTOM
     * TOP_BOTTOM 从上到下
     * TR_BL      右上到左下渐变
     * RIGHT_LEFT 右到左渐变
     * BR_TL      右下到左上渐变
     * BOTTOM_TOP 下到上渐变
     * BL_TR      左下到右上渐变
     * LEFT_RIGHT 左到右渐变
     * TL_BR      左上到右下渐变
     *
     * @param orientation GradientDrawable.Orientation orientation
     */
    public Shape setGradientOrientation(GradientDrawable.Orientation orientation) {
        this.gradientOrientation = orientation;
        return this;
    }

    /**
     * 设置渐变类型
     * GradientDrawable.LINEAR_GRADIENT 线性
     * GradientDrawable.RADIAL_GRADIENT 放射性
     * GradientDrawable.SWEEP_GRADIENT 扫描性
     *
     * @param gradientType 默认 GradientDrawable.LINEAR_GRADIENT
     */
    public Shape setGradientType(int gradientType) {
        this.gradientType = gradientType;
        return this;
    }

    /**
     * 设置了放射性 RADIAL_GRADIENT 渐变类型需要设置扫描的半径范围
     *
     * @param gradientRadius 半径范围
     */
    public Shape setGradientRadius(float gradientRadius) {
        this.gradientRadius = gradientRadius;
        return this;
    }

    /**
     * 设置圆角
     */
    public Shape setRadius(float radius) {
        this.isCorners = Boolean.TRUE;
        this.bottomLeftRadius = radius;
        this.bottomRightRadius = radius;
        this.topLeftRadius = radius;
        this.topRightRadius = radius;
        return this;
    }

    /**
     * 设置左下圆角
     */
    public Shape setBlRadius(float bottomLeftRadius) {
        this.isCorners = Boolean.TRUE;
        this.bottomLeftRadius = bottomLeftRadius;
        return this;
    }

    /**
     * 设置右下圆角
     */
    public Shape setBrRadius(float bottomRightRadius) {
        this.isCorners = Boolean.TRUE;
        this.bottomRightRadius = bottomRightRadius;
        return this;
    }

    /**
     * 设置左上圆角
     */
    public Shape setTlRadius(float topLeftRadius) {
        this.isCorners = Boolean.TRUE;
        this.topLeftRadius = topLeftRadius;
        return this;
    }

    /**
     * 设置右上圆角
     */
    public Shape setTrRadius(float topRightRadius) {
        this.isCorners = Boolean.TRUE;
        this.topRightRadius = topRightRadius;
        return this;
    }


    public Drawable create() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(shapeModel);

        if (isSolid) {
            drawable.setColor(solid);
        }

        if (isStroke) {
            drawable.setStroke(strokeWidth, strokeColor, strokeDashWidth, strokedDashGap);
        }

        if (isGradient) {
            drawable.setGradientType(gradientType);
            drawable.setOrientation(gradientOrientation);
            drawable.setColors(gradientColors);
            if (gradientType == GradientDrawable.RADIAL_GRADIENT) {
                drawable.setGradientRadius(gradientRadius);
            }
        }

        if (isCorners) {
            float[] radii = {
                    topLeftRadius, topLeftRadius,
                    topRightRadius, topRightRadius,
                    bottomRightRadius, bottomRightRadius,
                    bottomLeftRadius, bottomLeftRadius,
            };
            drawable.setCornerRadii(radii);
        }

        if (isSize) {
            drawable.setSize(width, height);
        }

        return drawable;
    }

}
