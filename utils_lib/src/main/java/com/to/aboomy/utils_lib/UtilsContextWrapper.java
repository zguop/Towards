package com.to.aboomy.utils_lib;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * auth aboom
 * date 2018/5/28
 */
public class UtilsContextWrapper {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
        AlertToast.init(sContext);
        USharedPref.init(sContext);
    }

    public static Context getAppContext() {
        if (sContext == null) {
            throw new IllegalStateException("should call init method first");
        }
        return sContext;
    }
}
