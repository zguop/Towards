package com.waitou.wt_library.manager;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.waitou.wt_library.R;
import com.waitou.wt_library.base.TitleBar;

/**
 * auth aboom
 * date 2019-05-19
 */
public class TitleBarManager {

    public static TitleBar attachViewGet(Context context) {
        return attachViewGet(context, true);
    }

    public static TitleBar attachViewGet(Context context, boolean attachToRoot) {
        TitleBar toolbar = new TitleBar(context);
        toolbar.setId(R.id.pageTitleBar);
        if (attachToRoot) {
            if (context instanceof Activity) {
                ViewGroup rootView = ((Activity) context).findViewById(R.id.pageRootView);
                rootView.addView(toolbar, 0);
            }
        }
        return toolbar;
    }
}
