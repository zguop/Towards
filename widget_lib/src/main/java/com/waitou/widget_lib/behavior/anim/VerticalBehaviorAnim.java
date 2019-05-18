package com.waitou.widget_lib.behavior.anim;

import android.animation.ValueAnimator;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

/**
 * auth aboom
 * date 2019-05-10
 */
public class VerticalBehaviorAnim extends BehaviorAnim {

    private float viewY;
    private float originalY;

    @Override
    public boolean initBehaviorAnimView(CoordinatorLayout coordinatorLayout, View child) {
        this.viewY = coordinatorLayout.getHeight() - child.getY();
        this.originalY = child.getY();
        return super.initBehaviorAnimView(coordinatorLayout, child);
    }

    @Override
    public void show() {
        setShowing(true);
        ValueAnimator animator = ValueAnimator.ofFloat(view.getY(), originalY);
        animator.setDuration(getDuration());
        animator.setInterpolator(getInterpolator());
        animator.addUpdateListener(valueAnimator -> view.setY((Float) valueAnimator.getAnimatedValue()));
        animator.start();
    }

    @Override
    public void hide() {
        setShowing(false);
        ValueAnimator animator = ValueAnimator.ofFloat(originalY, originalY + viewY);
        animator.setDuration(getDuration());
        animator.setInterpolator(getInterpolator());
        animator.addUpdateListener(valueAnimator -> view.setY((Float) valueAnimator.getAnimatedValue()));
        animator.start();
    }
}
