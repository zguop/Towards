package com.waitou.widget_lib.behavior;

import android.content.Context;
import android.util.AttributeSet;

import com.waitou.widget_lib.behavior.anim.IBehaviorAnim;
import com.waitou.widget_lib.behavior.anim.TitleBehaviorAnim;

/**
 * auth aboom
 * date 2019-05-10
 */
public class TitleBehavior extends Behavior {

    public TitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected IBehaviorAnim createBehaviorAnim() {
        return new TitleBehaviorAnim();
    }
}
