package com.to.aboomy.theme_lib.compat;

import android.content.Context;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * auth aboom
 * date 2019-08-22
 */
class SkinCompatViewInflater {

    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap<>();
    private static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private static final Object[] mConstructorArgs = new Object[2];
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if ("view".equals(name)) {
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
            throws InflateException {
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
}
