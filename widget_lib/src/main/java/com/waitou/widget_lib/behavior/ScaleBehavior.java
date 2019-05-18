package com.waitou.widget_lib.behavior;

import android.content.Context;
import android.util.AttributeSet;

import com.waitou.widget_lib.behavior.anim.IBehaviorAnim;
import com.waitou.widget_lib.behavior.anim.ScaleBehaviorAnim;

/**
 * auth aboom
 * date 2019-05-10
 */
public class ScaleBehavior extends Behavior {

    public ScaleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected IBehaviorAnim createBehaviorAnim() {
        return new ScaleBehaviorAnim();
    }
}
