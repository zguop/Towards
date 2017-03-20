package com.waitou.wt_library.theme;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.waitou.wt_library.R;
import com.waitou.wt_library.cache.SharedPref;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 主题切换控制器
 * 支持@开头？开头属性
 * 可自定义主题属性 ?attr/
 * 支出属性 @color/ @drawable/
 * 文件命名 以skin开头
 * 默认定义了colorPrimary colorPrimaryDark colorAccent 主题颜色
 * 支持的属性可在SkinAttrType 枚举中增加
 * 自定义view可实现 SkinCompatSupportable 例子：CustomCircleImageView
 * <p>
 * 使用方式：
 * 1.style定义主题
 * 2.在ThemeEnum增加枚举
 */
public class ChangeModeController {

    private static final String COLOR_PRIMARY      = "colorPrimary";
    private static final String COLOR_PRIMARY_DARK = "colorPrimaryDark";
    private static final String COLOR_ACCENT       = "colorAccent";
    private static final String ATTR_PREFIX        = "skin"; //开头

    private static final String PRE_THEME_MODEL = "theme_model"; //sp 保存当前的主题

    private static final Object[] mConstructorArgs = new Object[2];

    private static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};

    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap<>();

    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    private List<SkinView> mSkinViewList = new ArrayList<>();
    private static ChangeModeController sChangeModeController;

    private ChangeModeController() {
    }

    public static ChangeModeController get() {
        if (sChangeModeController == null) {
            synchronized (ChangeModeController.class) {
                if (sChangeModeController == null) {
                    sChangeModeController = new ChangeModeController();
                }
            }
        }
        return sChangeModeController;
    }

    public void init(final AppCompatActivity activity) {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(activity), (parent, name, context, attrs) -> {
            List<SkinAttr> skinAttrsList = getSkinAttrs(attrs, context);
            //如果属性为null 并且名字没有包含. 说明不是自定义的view
            if (skinAttrsList == null || skinAttrsList.isEmpty()) {
                if (!name.contains(".")) {
                    return null;
                }
            }
            View view = activity.getDelegate().createView(parent, name, activity, attrs);
            if (view == null) {
                view = createViewFromTag(context, name, attrs);
            }
            if (view != null) {
                if (skinAttrsList == null && !(view instanceof SkinCompatSupportable)) {
                    return null;
                }
                mSkinViewList.add(new SkinView(view, skinAttrsList));
            }
            return view;
        });
    }

//    private View createViews(Context context, String name, AttributeSet attrs) {
//        View view = null;
//        try {
//            if (-1 == name.indexOf('.')) {
//                if ("View".equals(name)) {
//                    view = LayoutInflater.from(context).createView(name, "android.view.", attrs);
//                }
//                if (view == null) {
//                    view = LayoutInflater.from(context).createView(name, "android.widget.", attrs);
//                }
//                if (view == null) {
//                    view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs);
//                }
//            } else {
//                view = LayoutInflater.from(context).createView(name, null, attrs);
//            }
//        } catch (Exception e) {
//            if (BuildConfig.DEBUG) {
//                LogUtil.e(TAG, "error while create 【" + name + "】 : " + e.getMessage());
//            }
//            view = null;
//        }
//        return view;
//    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;
            if (-1 == name.indexOf('.')) {
                // try the android.widget prefix first...
                for (String aSClassPrefixList : sClassPrefixList) {
                    final View view = createView(context, name, aSClassPrefixList);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }

    private View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        }
    }

    private List<SkinAttr> getSkinAttrs(AttributeSet attrs, Context context) {
        List<SkinAttr> skinAttrsList = null;
        SkinAttr skinAttr;
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attributeName = attrs.getAttributeName(i);
            SkinAttrType attrType = getSupportAttrType(attributeName);
            if (attrType == null) {
                continue;
            }
            String attributeValue = attrs.getAttributeValue(i);
            if (attributeValue.startsWith("?") || attributeValue.startsWith("@")) {
                int id = ThemeUtils.getAttrResId(attributeValue);
                String entryName = context.getResources().getResourceEntryName(id);
                if (entryName.equals(COLOR_PRIMARY) || entryName.equals(COLOR_PRIMARY_DARK) || entryName.equals(COLOR_ACCENT) || entryName.startsWith(ATTR_PREFIX)) {
                    if (skinAttrsList == null) {
                        skinAttrsList = new ArrayList<>();
                    }
                    String typeName = context.getResources().getResourceTypeName(id);
                    skinAttr = new SkinAttr(attrType, entryName, attributeName, typeName);
                    skinAttrsList.add(skinAttr);
                }
            }
        }
        return skinAttrsList;
    }

    private SkinAttrType getSupportAttrType(String attrName) {
        for (SkinAttrType attrType : SkinAttrType.values()) {
            if (attrType.getAttrType().equals(attrName))
                return attrType;
        }
        return null;
    }

    /**
     * 获取当前的主题model
     */
    public ThemeEnum getThemeModel() {
        int themeId = SharedPref.get().getInteger(PRE_THEME_MODEL);
        return ThemeEnum.valueOf(themeId);
    }

    /**
     * 设置当前主题
     */
    public void setTheme(Activity ctx) {
        int theme = SharedPref.get().getInteger(PRE_THEME_MODEL);
        ThemeEnum themeEnum = ThemeEnum.valueOf(theme);
        ctx.setTheme(themeEnum.getTheme());
    }

    /**
     * 更改主题
     */
    public void changeNight(Activity ctx, ThemeEnum themeEnum) {
        ctx.setTheme(themeEnum.getTheme());
        showAnimation(ctx);
        refreshUI(ctx);
        SharedPref.get().put(PRE_THEME_MODEL, themeEnum.getTheme());
    }

    /**
     * 刷新UI界面
     */
    private void refreshUI(Activity ctx) {
        refreshStatusBar(ctx);
        for (SkinView skinView : mSkinViewList) {
            skinView.apply();
        }
    }

    /**
     * 刷新 StatusBar
     */
    private void refreshStatusBar(Activity ctx) {
        if (Build.VERSION.SDK_INT >= 21) {
            int resourceId = ThemeUtils.getThemeAttrId(ctx, R.attr.colorPrimaryDark);
            ctx.getWindow().setStatusBarColor(ActivityCompat.getColor(ctx, resourceId));
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
            objectAnimator.setDuration(500);
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

    enum SkinAttrType {
        BACKGROUD("background") {
            @Override
            public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
                Context context = view.getContext();
                view.setBackground(ThemeUtils.getDrawable(context, getResId(context, attrName)));
            }
        },
        COLOR("textColor") {
            @Override
            public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
                Context context = view.getContext();
                ((TextView) view).setTextColor(ThemeUtils.getColorStateList(context, getResId(context, attrName)));
            }
        },
        SRC("src") {
            @Override
            public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
                Context context = view.getContext();
                ((ImageView) view).setImageDrawable(ThemeUtils.getDrawable(context, getResId(context, attrName)));
            }
        },
        BACKGROUNDTINT("backgroundTint") {
            @Override
            public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
                Context context = view.getContext();
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    view.setBackgroundTintList(ThemeUtils.getColorStateList(context, getResId(context, attrName)));
                }
            }
        }, TINT("tint") {
            @Override
            public void apply(View view, String attrName, String attrValueResName, String attrValueTypeName) {
                Context context = view.getContext();
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    ((ImageView) view).setImageTintList(ThemeUtils.getColorStateList(context, getResId(context, attrName)));
                }
            }
        };

        private String attrType;

        SkinAttrType(String attrType) {
            this.attrType = attrType;
        }

        public String getAttrType() {
            return attrType;
        }

        public abstract void apply(View view, String attrName, String attrValueResName, String attrValueTypeName);

        public int getResId(Context context, String resName) {
            switch (resName) {
                case COLOR_PRIMARY:
                    return ThemeUtils.getThemeAttrId(context, R.attr.colorPrimary);
                case COLOR_PRIMARY_DARK:
                    return ThemeUtils.getThemeAttrId(context, R.attr.colorPrimaryDark);
                case COLOR_ACCENT:
                    return ThemeUtils.getThemeAttrId(context, R.attr.colorAccent);
                default:
                    return ThemeUtils.getIdentifier(context, resName, context.getPackageName());
            }
        }
    }

    public void cancel() {
        mSkinViewList.clear();
        sChangeModeController = null;
    }
}
