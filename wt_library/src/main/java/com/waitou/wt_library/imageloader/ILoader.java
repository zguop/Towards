package com.waitou.wt_library.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.File;


/**
 * Created by wanglei on 2016/11/27.
 */

public interface ILoader {

    void init(Context context);

    void loadNet(ImageView target, String url, Options options);

    void loadNet(Context context, String url, Options options, LoadCallback callback);

    void loadResource(ImageView target, int resId, Options options);

    void loadAssets(ImageView target, String assetName, Options options);

    void loadFile(ImageView target, File file, Options options);

    void clearMemoryCache(Context context);

    void clearDiskCache(Context context);

    void resume(Context context);

    void pause(Context context);


    class Options {

        int loadingResId   = RES_NONE;        //加载中的资源id
        int loadErrorResId = RES_NONE;      //加载失败的资源id
        Drawable loadingDrawable;
        Drawable loadErrorDrawable;

        static final int RES_NONE = -1;

        static Options defaultOptions() {
            return new Options(RES_NONE, RES_NONE);
        }

        public Options(int loadingResId, int loadErrorResId) {
            this.loadingResId = loadingResId;
            this.loadErrorResId = loadErrorResId;
        }

        public Options(Drawable loadingDrawable, Drawable loadErrorDrawable) {
            this.loadingDrawable = loadingDrawable;
            this.loadErrorDrawable = loadErrorDrawable;
        }
    }

}
