package com.waitou.wt_library.base;

import android.view.View;

/**
 * Created by wanglei on 2016/12/1.
 */

public interface VDelegate {

    void resume();

    void pause();

    void destroy();

    void flagVisible(boolean flag, View view);

    void visible(View view);

    void flagGone(boolean flag, View view);

    void gone(View view);

    void inVisible(View view);
}
