package com.waitou.photopicker;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * auth aboom
 * date 2019-05-30
 */
public class Wisdom {

    private WeakReference<Object> sojournReference;

    private Wisdom(Object sojourn) {
        sojournReference = new WeakReference<>(sojourn);
    }

    public static Wisdom of(Activity activity) {
        return new Wisdom(activity);
    }

    public static Wisdom of(Fragment fragment) {
        return new Wisdom(fragment);
    }


    public void open() {

    }
}
