package com.waitou.lib_theme;


/**
 * Created by waitou on 17/1/17.
 * style定义几套在这里定义几套
 */

public enum ThemeModel {

    POWDER(R.style.AppThemePowder, R.color.colorPowder, "powder"),
    RED(R.style.AppThemeRed, R.color.colorRed, "red");

    private int    theme;
    private int    colorId;
    private String themeStr;

    ThemeModel(int theme, int colorId, String themeStr) {
        this.theme = theme;
        this.colorId = colorId;
        this.themeStr = themeStr;
    }

    public int getTheme() {
        return theme;
    }

    public String getThemeStr() {
        return themeStr;
    }

    public int getColorId() {
        return colorId;
    }

    public static ThemeModel valueOf(int theme) {
        if (theme == R.style.AppThemeRed) {
            return RED;
        } else if (theme == R.style.AppThemePowder) {
            return POWDER;
        } else {
            return RED;
        }
    }
}
