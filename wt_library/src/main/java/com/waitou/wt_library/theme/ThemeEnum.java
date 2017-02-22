package com.waitou.wt_library.theme;

import com.waitou.wt_library.R;

/**
 * Created by waitou on 17/2/14.
 */

public enum ThemeEnum {

    POWDER(R.style.AppThemePowder, R.color.colorPowder, "powder"),
    RED(R.style.AppThemeRed, R.color.colorRed, "red"),
    BLUE(R.style.AppThemeBlue, R.color.colorBlue, "blue");

    private int    theme;
    private int    colorId;
    private String themeStr;

    ThemeEnum(int theme, int colorId, String themeStr) {
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

    public static ThemeEnum valueOf(int theme) {
        if (theme == R.style.AppThemeRed) {
            return RED;
        } else if (theme == R.style.AppThemePowder) {
            return POWDER;
        } else if (theme == R.style.AppThemeBlue) {
            return BLUE;
        } else {
            return RED;
        }
    }
}
