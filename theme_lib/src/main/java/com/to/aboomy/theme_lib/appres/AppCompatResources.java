/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.to.aboomy.theme_lib.appres;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;

import org.xmlpull.v1.XmlPullParser;

/**
 * Class for accessing an application's resources through AppCompat, and thus any backward
 * compatible functionality.
 */
public final class AppCompatResources {

    private static final String LOG_TAG = "AppCompatResources";
    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<>();

    private AppCompatResources() {}

    /**
     * Returns the {@link ColorStateList} from the given resource. The resource can include
     * themeable attributes, regardless of API level.
     *
     * @param context context to inflate against
     * @param resId the resource identifier of the ColorStateList to retrieve
     */
    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= 23) {
            // On M+ we can use the framework
            return context.getColorStateList(resId);
        }
        // Cache miss, so try and inflate it ourselves
        ColorStateList csl = inflateColorStateList(context, resId);
        if (csl != null) {
            return csl;
        }

        // If we reach here then we couldn't inflate it, so let the framework handle it
        return ContextCompat.getColorStateList(context, resId);
    }

    /**
     * Inflates a {@link ColorStateList} from resources, honouring theme attributes.
     */
    @Nullable
    private static ColorStateList inflateColorStateList(Context context, int resId) {
        if (isColorInt(context, resId)) {
            // The resource is a color int, we can't handle it so return null
            return null;
        }

        final Resources r = context.getResources();

        try {
            final XmlPullParser xml = r.getXml(resId);
            return AppCompatColorStateListInflater.createFromXml(r, xml, context.getTheme());
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to inflate ColorStateList, leaving it to the framework", e);
        }
        return null;
    }

    private static boolean isColorInt(@NonNull Context context, @ColorRes int resId) {
        final Resources r = context.getResources();

        final TypedValue value = getTypedValue();
        r.getValue(resId, value, true);

        return value.type >= TypedValue.TYPE_FIRST_COLOR_INT
                && value.type <= TypedValue.TYPE_LAST_COLOR_INT;
    }

    @NonNull
    private static TypedValue getTypedValue() {
        TypedValue tv = TL_TYPED_VALUE.get();
        if (tv == null) {
            tv = new TypedValue();
            TL_TYPED_VALUE.set(tv);
        }
        return tv;
    }
}
