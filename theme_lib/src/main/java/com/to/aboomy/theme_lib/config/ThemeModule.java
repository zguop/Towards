package com.to.aboomy.theme_lib.config;

/**
 * auth aboom
 * date 2018/5/27
 */
public class ThemeModule {

    private int    themeId;
    private int    colorId;
    private String themeStr;

    public ThemeModule(int themeId, int colorId, String themeStr) {
        this.themeId = themeId;
        this.colorId = colorId;
        this.themeStr = themeStr;
    }

    public int getThemeId() {
        return themeId;
    }

    public int getColorId() {
        return colorId;
    }

    public String getThemeStr() {
        return themeStr;
    }
}
