package com.waitou.widget_lib.behavior.anim;

import android.view.View;
import android.view.animation.Interpolator;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * auth aboom
 * date 2019-05-10
 */
public interface IBehaviorAnim {

    boolean initBehaviorAnimView(CoordinatorLayout coordinatorLayout, View child);

    Interpolator getInterpolator();

    void setInterpolator(Interpolator interpolator);

    int getDuration();

    void setDuration(int duration);

    void show();

    void hide();

    boolean isShowing();

    void setShowing(boolean showing);
}
