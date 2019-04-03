package com.waitou.towards.common;

import android.content.Context;

/**
 * Created by waitou on 17/5/25.
 */

public enum NativeEnum {
    TYPE_H5(Values.TYPE_H5, Values.PATH_H5),
    TYPE_MOVIE_RECOMMEND(Values.TYPE_MOVIE_RECOMMEND, Values.PATH_MOVIE_RECOMMEND),
    TYPE_DEFAULT(Values.TYPE_DEFAULT, "");

    private int    type;
    private String path;

    NativeEnum(int type, String path) {
        this.type = type;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static NativeEnum valueOf(int type) {
        for (NativeEnum nativeEnum : NativeEnum.values()) {
            if (type == nativeEnum.type) {
                return nativeEnum;
            }
        }
        return TYPE_DEFAULT;
    }

    public static void go(Context context, int nativeType, String nativeValue) {
        NativeEnum nativeEnum = NativeEnum.valueOf(nativeType);
        IntentUtil.go(context, nativeEnum.getPath(), nativeValue);
    }
}
