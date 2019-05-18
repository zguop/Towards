package com.waitou.widget_lib.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;

/**
 * auth aboom
 * date 2019-05-10
 */
public class BehaviorUtils {

    public static Behavior from(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
        if (!(behavior instanceof Behavior)) {
            throw new IllegalArgumentException("The view's behavior isn't an instance of Behavior. " +
                    "Try to check the [app:layout_behavior]");
        }
        return ((Behavior) behavior);
    }
}
