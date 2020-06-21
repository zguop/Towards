package com.waitou.widget_lib.behavior.anim;

import android.animation.ValueAnimator;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * auth aboom
 * date 2019-05-10
 */
public class BottomBehaviorAnim extends BehaviorAnim {

    private float originalY;

    @Override
    public boolean initBehaviorAnimView(CoordinatorLayout coordinatorLayout, View child) {
        this.originalY = child.getY();
        return super.initBehaviorAnimView(coordinatorLayout, child);
    }

    @Override
    public void show() {
        setShowing(true);
        ValueAnimator animator = ValueAnimator.ofFloat(view.getY(), originalY);
        animator.setDuration(getDuration());
        animator.setInterpolator(getInterpolator());
        animator.addUpdateListener(valueAnimator ->
                view.setY((float) valueAnimator.getAnimatedValue())
        );
        animator.start();
    }

    @Override
    public void hide() {
        setShowing(false);
        ValueAnimator animator = ValueAnimator.ofFloat(view.getY(), originalY + view.getHeight());
        animator.setDuration(getDuration());
        animator.setInterpolator(getInterpolator());
        animator.addUpdateListener(valueAnimator ->
                view.setY((float) valueAnimator.getAnimatedValue())
        );
        animator.start();
    }
}
