package com.waitou.towards.common;

import com.to.aboomy.theme_lib.config.IThemeConfig;
import com.to.aboomy.theme_lib.config.ThemeModule;
import com.waitou.towards.R;

import java.util.ArrayList;
import java.util.List;

/**
 * auth aboom
 * date 2018/5/27
 */
public class ThemeImpl implements IThemeConfig {
    @Override
    public List<ThemeModule> getTheme() {
        List<ThemeModule> list = new ArrayList<>();
        list.add(new ThemeModule(R.style.AppThemePowder, R.color.colorPowder, "powder"));
        list.add(new ThemeModule(R.style.AppThemeRed, R.color.colorRed, "red"));
        list.add(new ThemeModule(R.style.AppThemeBlue, R.color.colorBlue, "blue"));
        return list;
    }
}
