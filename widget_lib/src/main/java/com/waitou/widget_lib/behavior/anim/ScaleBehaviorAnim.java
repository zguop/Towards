package com.waitou.widget_lib.behavior.anim;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;

/**
 * auth aboom
 * date 2019-05-10
 */
public class ScaleBehaviorAnim extends BehaviorAnim {

    @Override
    public void show() {
        setShowing(true);
        ViewCompat.animate(view).scaleX(1f).scaleY(1f)
                .setDuration(getDuration())
                .setInterpolator(getInterpolator())
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        view.setVisibility(View.VISIBLE);
                    }
                }).start();
    }

    @Override
    public void hide() {
        setShowing(false);
        ViewCompat.animate(view).scaleX(0f).scaleY(0f)
                .setDuration(getDuration())
                .setInterpolator(getInterpolator())
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(View view) {
                        view.setVisibility(View.INVISIBLE);
                    }
                }).start();
    }
}
