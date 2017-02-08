package cn.droidlover.xdroid.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

/**
 * Created by waitou on 17/1/9.
 */

public interface UIView<P>  extends IView<P>{

    boolean initXView();

    @LayoutRes
    int getContentViewId();

    void initData(Bundle savedInstanceState);

    void reloadData();
}
