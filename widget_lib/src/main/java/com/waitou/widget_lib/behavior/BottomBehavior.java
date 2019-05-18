package com.waitou.widget_lib.behavior;

import android.content.Context;
import android.util.AttributeSet;

import com.waitou.widget_lib.behavior.anim.BottomBehaviorAnim;
import com.waitou.widget_lib.behavior.anim.IBehaviorAnim;

/**
 * auth aboom
 * date 2019-05-10
 */
public class BottomBehavior extends Behavior {

    public BottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected IBehaviorAnim createBehaviorAnim() {
        return new BottomBehaviorAnim();
    }

}
