package com.to.aboomy.theme_lib.compat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.LayoutInflaterCompat;

import com.to.aboomy.theme_lib.annotation.Skinable;
import com.to.aboomy.theme_lib.utils.SkinPreference;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * auth aboom
 * date 2019-08-22
 */
public class SkinActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private static volatile SkinActivityLifecycle sInstance;

    private WeakHashMap<Context, SkinCompatDelegate> skinDelegateMap;

    /**
     * 用于记录当前Activity，在换肤后，立即刷新当前Activity以及非Activity创建的View。
     */
    private WeakReference<Activity> curActivityRef;

    private SkinActivityLifecycle(Application application) {
        application.registerActivityLifecycleCallbacks(this);
    }

    private void installLayoutFactory(Context context) {
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            LayoutInflaterCompat.setFactory2(layoutInflater, getSkinDelegate(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SkinActivityLifecycle init(Application application) {
        if (sInstance == null) {
            synchronized (SkinActivityLifecycle.class) {
                if (sInstance == null) {
                    sInstance = new SkinActivityLifecycle(application);
                }
            }
        }
        return sInstance;
    }

    private SkinCompatDelegate getSkinDelegate(Context context) {
        if (skinDelegateMap == null) {
            skinDelegateMap = new WeakHashMap<>();
        }
        SkinCompatDelegate skinCompatDelegate = skinDelegateMap.get(context);
        if (skinCompatDelegate == null) {
            skinCompatDelegate = new SkinCompatDelegate();
            skinDelegateMap.put(context, skinCompatDelegate);
        }
        return skinCompatDelegate;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        int styleResId = SkinPreference.getInstance().getStyleResId();
        if (styleResId != SkinPreference.NONE) {
            activity.setTheme(styleResId);
        }
        if (isSkinEnable(activity)) {
            installLayoutFactory(activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (isSkinEnable(activity)) {
            curActivityRef = new WeakReference<>(activity);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (isSkinEnable(activity)) {
            skinDelegateMap.remove(activity);
        }
    }

    public void applySkin(int styleResId) {
        try {
            boolean apply = curActivityRef != null && curActivityRef.get() != null;
            if (apply) {
                Activity activity = curActivityRef.get();
                activity.setTheme(styleResId);
                showAnimation(activity);
                if (activity instanceof SkinCompatSupportable) {
                    ((SkinCompatSupportable) activity).applySkin();
                }
                getSkinDelegate(activity).applySkin();
                SkinPreference.getInstance().setStyleResId(styleResId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 展示切换动画
     */
    private void showAnimation(Activity ctx) {
        final View decorView = ctx.getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(ctx);
            view.setBackground(new BitmapDrawable(ctx.getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);
            ValueAnimator objectAnimator = ValueAnimator.ofFloat(1f, 0f);//view, "alpha",
            objectAnimator.setDuration(1000);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);
                }
            });
            objectAnimator.addUpdateListener(animation -> {
                float alpha = (Float) animation.getAnimatedValue();
                view.setAlpha(alpha);
            });
            objectAnimator.start();
        }
    }

    /**
     * 获取一个 View 的缓存视图
     */
    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    private boolean isSkinEnable(Context context) {
        return context.getClass().getAnnotation(Skinable.class) != null;
    }
}
