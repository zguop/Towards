package com.waitou.wt_library.kit;

import android.content.Context;

/**
 * Created by waitou on 17/3/24.
 */

public class UImage {

    private static final String JPG = ".jpg";
    private static final String PNG = ".png";

    /**
     * 获取下载图片URL的的文件保存路径
     */
    public static String getImageSavePath(Context context, String url) {
        java.io.File file = context.getCacheDir();
        if (file != null && file.exists()) {
        } else {
            file = context.getFilesDir();
        }
        return file.getAbsolutePath() + java.io.File.separator + getImageFileNameFromURL(url);
    }

    /**
     * 获取下载图片URL的文件名
     */
    private static String getImageFileNameFromURL(String url) {
        if (url.endsWith(PNG)) {
            return Codec.MD5.getMd5Key(url) + PNG;
        } else {
            return Codec.MD5.getMd5Key(url) + JPG;
        }
    }
}
