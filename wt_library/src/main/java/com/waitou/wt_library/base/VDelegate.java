package com.waitou.wt_library.base;

import android.view.View;

/**
 * Created by wanglei on 2016/12/1.
 */

public interface VDelegate {

    void resume();
    void pause();
    void destroy();

    void visible(boolean flag, View view);
    void gone(boolean flag, View view);
    void inVisible(View view);


}
