package com.waitou.wt_library.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

/**
 * Created by waitou on 17/1/9.
 */

public interface UIView<P> extends IView<P> {

    boolean defaultXView();

    boolean defaultLoading();

    @LayoutRes
    int getContentViewId();

    void initData(Bundle savedInstanceState);

    void reloadData();

    P createPresenter();

}
