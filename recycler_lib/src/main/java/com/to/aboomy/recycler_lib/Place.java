package com.to.aboomy.recycler_lib;

import android.graphics.Color;

/**
 * auth aboom
 * date 2019-05-06
 */
public class Place implements Displayable {

    /**
     * 占位高度
     */
    private int height;
    /**
     * 左间距
     */
    private int leftMargin;
    /**
     * 背景色
     */
    private int backgroundColor;


    public int getHeight() {
        return height;
    }

    public int getLeftMargin() {
        return leftMargin;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public static Place createPlace(int height) {
        Place placeInfo = new Place();
        placeInfo.height = height;
        placeInfo.backgroundColor = Color.TRANSPARENT;
        return placeInfo;
    }

    public static Place createPlaceLine() {
        Place placeInfo = new Place();
        placeInfo.height = 1;
        placeInfo.backgroundColor = Color.GRAY;
        return placeInfo;
    }

    public static Place createPlaceLine(int leftMargin) {
        Place placeLine = createPlaceLine();
        placeLine.leftMargin = leftMargin;
        return placeLine;
    }

}
