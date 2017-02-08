package com.waitou.lib_theme;


/**
 * Created by waitou on 17/1/17.
 * style定义几套在这里定义几套
 */

public enum ThemeModel {

    POWDER(R.style.AppThemePowder, "powder"),
    RED(R.style.AppThemeRed, "red");

    private int    theme;
    private String themeStr;

    ThemeModel(int theme, String themeStr) {
        this.theme = theme;
        this.themeStr = themeStr;

    }

    public int getTheme() {
        return theme;
    }

    public String getThemeStr() {
        return themeStr;
    }

    public static ThemeModel valueOf(int theme) {
        if (theme == R.style.AppThemePowder) {
            return POWDER;
        } else {
            return RED;
        }
    }
}
