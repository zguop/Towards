package com.to.aboomy.recycler_lib;

import android.content.res.Resources;
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
        return createPlace(height, Color.TRANSPARENT);
    }

    public static Place createPlace(int height, int backgroundColor) {
        Place placeInfo = new Place();
        placeInfo.height = height;
        placeInfo.backgroundColor = backgroundColor;
        return placeInfo;
    }

    public static Place createPlaceDp(int height) {
        return createPlace((int) (height * Resources.getSystem().getDisplayMetrics().density + 0.5f));
    }

    public static Place createPlaceDp(int height, int backgroundColor) {
        return createPlace((int) (height * Resources.getSystem().getDisplayMetrics().density + 0.5f), backgroundColor);
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
