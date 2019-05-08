package com.waitou.wt_library.base;

import android.view.View;

/**
 * auth aboom
 * date 2019-05-05
 */
public class BasePageFragment extends BaseFragment implements IView {
    @Override
    public View getContentView() {
        return null;
    }

    @Override
    public Runnable run() {
        return runnable;
    }

    private Runnable runnable = () -> {
    };
}
