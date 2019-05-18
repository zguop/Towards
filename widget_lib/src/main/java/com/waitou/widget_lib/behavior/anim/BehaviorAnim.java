package com.waitou.widget_lib.behavior.anim;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * auth aboom
 * date 2019-05-10
 */
public abstract class BehaviorAnim implements IBehaviorAnim {

    private Interpolator interpolator = new LinearOutSlowInInterpolator();
    private int          duration     = 400;
    private boolean      isShowing;

    protected View view;

    @Override
    public boolean initBehaviorAnimView(CoordinatorLayout coordinatorLayout, View child) {
        this.view = child;
        this.isShowing = Boolean.TRUE;
        return true;
    }

    @Override
    public Interpolator getInterpolator() {
        return interpolator;
    }

    @Override
    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }
}
