package com.waitou.wt_library.base;

import android.os.Bundle;

/**
 * Created by waitou on 17/1/9.
 */

public interface UIView<P> {

    int getContentViewId();

    void afterCreate(Bundle savedInstanceState);

    void reloadData();

    P createPresenter();

}
