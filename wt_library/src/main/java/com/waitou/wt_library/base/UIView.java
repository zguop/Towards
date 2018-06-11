package com.waitou.wt_library.base;

import android.os.Bundle;

/**
 * Created by waitou on 17/1/9.
 */

public interface UIView<P> extends IView<P> {

    boolean defaultXView();

    boolean defaultLoading();

    int getContentViewId();

    void initData(Bundle savedInstanceState);

    void reloadData();

    P createPresenter();

}
