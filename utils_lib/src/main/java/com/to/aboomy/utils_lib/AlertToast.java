package com.to.aboomy.utils_lib;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by waitou on 17/1/9.
 */

public class AlertToast {
    private static Context sAppContext;
    private static Toast   sToast;

    public static void init(Context c) {
        sAppContext = c;
    }

    public static void show(String text, int duration) {
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(sAppContext, text, duration);
        sToast.show();
    }

    public static void show(String text) {
        AlertToast.show(text, Toast.LENGTH_SHORT);
    }

    public static void show(int text, int duration) {
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(sAppContext, text, duration);
        sToast.show();
    }

    public static void show(int text) {
        AlertToast.show(text, Toast.LENGTH_SHORT);
    }
}
