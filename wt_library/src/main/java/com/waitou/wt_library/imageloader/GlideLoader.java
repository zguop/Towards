package com.waitou.wt_library.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.waitou.net_library.http.AsyncOkHttpClient;

import java.io.File;
import java.io.InputStream;

/**
 * Created by wanglei on 2016/11/28.
 */

public class GlideLoader implements ILoader {

    @Override
    public void init(Context context) {
        Glide.get(context).register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(AsyncOkHttpClient.getOkHttpClient()));
    }

    @Override
    public void loadNet(ImageView target, String url, Options options) {
        load(getRequestManager(target.getContext()).load(url), options).into(target);
    }

    @Override
    public void loadCenterCropNet(ImageView target, String url, Options options) {
        load(getRequestManager(target.getContext()).load(url), options).centerCrop().into(target);
    }

    @Override
    public void loadNet(Context context, String url, Options options, final LoadCallback callback) {
        DrawableTypeRequest request = load(getRequestManager(context).load(url), options);
        request.into(new SimpleTarget<GlideBitmapDrawable>() {

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                if (callback != null) {
                    callback.onLoadFailed(e);
                }
            }

            @Override
            public void onResourceReady(GlideBitmapDrawable resource, GlideAnimation<? super GlideBitmapDrawable> glideAnimation) {
                if (resource != null && resource.getBitmap() != null) {
                    if (callback != null) {
                        callback.onLoadReady(resource.getBitmap());
                    }
                }
            }

        });
    }

    @Override
    public void loadTransformNet(Context context, String url, Options options, Transformation transformation, LoadCallback callback) {
        DrawableTypeRequest request = load(getRequestManager(context).load(url), options);
        request.transform(transformation);
        request.into(new SimpleTarget<GlideBitmapDrawable>() {
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                if (callback != null) {
                    callback.onLoadFailed(e);
                }
            }

            @Override
            public void onResourceReady(GlideBitmapDrawable resource, GlideAnimation<? super GlideBitmapDrawable> glideAnimation) {
                if (resource != null && resource.getBitmap() != null) {
                    if (callback != null) {
                        callback.onLoadReady(resource.getBitmap());
                    }
                }
            }
        });
    }

    @Override
    public void loadResource(ImageView target, int resId, Options options) {
        load(getRequestManager(target.getContext()).load(resId), options).into(target);
    }

    @Override
    public void loadAssets(ImageView target, String assetName, Options options) {
        load(getRequestManager(target.getContext()).load("file:///android_asset/" + assetName), options).into(target);
    }

    @Override
    public void loadFile(ImageView target, File file, Options options) {
        load(getRequestManager(target.getContext()).load(file), options).into(target);
    }

    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    @Override
    public void resume(Context context) {
        getRequestManager(context).resumeRequests();
    }

    @Override
    public void pause(Context context) {
        getRequestManager(context).pauseRequests();
    }

    private RequestManager getRequestManager(Context context) {
        if (context instanceof Activity) {
            return Glide.with((Activity) context);
        }
        return Glide.with(context);
    }

    private DrawableTypeRequest load(DrawableTypeRequest request, Options options) {
        if (options == null) {
            options = Options.defaultOptions();
        }

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }
        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }

        if (options.loadingDrawable != null) {
            request.placeholder(options.loadingDrawable);
        }

        if (options.loadErrorDrawable != null) {
            request.error(options.loadErrorDrawable);
        }
        request.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade();

        return request;
    }
}
