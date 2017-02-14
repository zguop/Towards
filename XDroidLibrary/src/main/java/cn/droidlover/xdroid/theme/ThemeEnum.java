package cn.droidlover.xdroid.theme;

/**
 * Created by waitou on 17/2/14.
 */

public enum  ThemeEnum {

    POWDER(com.waitou.lib_theme.R.style.AppThemePowder, com.waitou.lib_theme.R.color.colorPowder, "powder"),
    RED(com.waitou.lib_theme.R.style.AppThemeRed, com.waitou.lib_theme.R.color.colorRed, "red");

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
        if (theme == com.waitou.lib_theme.R.style.AppThemeRed) {
            return RED;
        } else if (theme == com.waitou.lib_theme.R.style.AppThemePowder) {
            return POWDER;
        } else {
            return RED;
        }
    }
}
