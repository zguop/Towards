package com.to.aboomy.theme_lib.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * auth aboom
 * date 2019-08-22
 */
public class SkinPreference {

    public static final int NONE = 0;

    private static final String FILE_NAME = "skin_meta-data";

    private static final String PRE_THEME_MODEL = "skin_theme_id"; //sp 保存当前的主题




    private static SkinPreference SKIN_PREFERENCE;

    private final SharedPreferences preferences;


    private SkinPreference(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void init(Application application) {
        if (SKIN_PREFERENCE == null) {
            synchronized (SkinPreference.class) {
                if (SKIN_PREFERENCE == null) {
                    SKIN_PREFERENCE = new SkinPreference(application);
                }
            }
        }
    }

    public static SkinPreference getInstance() {
        return SKIN_PREFERENCE;
    }

    public void setStyleResId(int styleResId) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(PRE_THEME_MODEL, styleResId);
        edit.apply();
    }

    public int getStyleResId() {
        return preferences.getInt(PRE_THEME_MODEL, NONE);
    }
}
