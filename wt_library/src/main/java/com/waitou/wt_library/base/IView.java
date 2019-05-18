package com.waitou.wt_library.base;

import android.view.View;
import android.view.ViewGroup;

/**
 * auth aboom
 * date 2019/4/7
 */
public interface IView {

    default ViewGroup initRootView() { return null; }

    View getContentView();

    Runnable run();
}
