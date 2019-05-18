package com.waitou.widget_lib.behavior.anim;

import android.animation.ValueAnimator;

/**
 * auth aboom
 * date 2019-05-10
 */
public class TitleBehaviorAnim extends BehaviorAnim {

    @Override
    public void show() {
        setShowing(true);
        ValueAnimator animator = ValueAnimator.ofFloat(view.getY(), 0);
        animator.setDuration(getDuration());
        animator.setInterpolator(getInterpolator());
        animator.addUpdateListener(valueAnimator -> view.setY((Float) valueAnimator.getAnimatedValue()));
        animator.start();
    }

    @Override
    public void hide() {
        setShowing(false);
        ValueAnimator animator = ValueAnimator.ofFloat(view.getY(), -view.getHeight());
        animator.setDuration(getDuration());
        animator.setInterpolator(getInterpolator());
        animator.addUpdateListener(valueAnimator -> view.setY((Float) valueAnimator.getAnimatedValue()));
        animator.start();
    }
}
