package com.waitou.widget_lib.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.waitou.widget_lib.behavior.anim.IBehaviorAnim;

/**
 * auth aboom
 * date 2019-05-10
 */
public abstract class Behavior extends CoordinatorLayout.Behavior<View> {

    private boolean initialize;

    /**
     * 单次滑动的总距离
     */
    private int totalScrollY;

    /**
     * 出发滑动动画最小距离
     */
    private int minScrollY;
    /**
     * 设置最小滑动距离
     */
    private int scrollYDistance;

    private boolean isEnableScroll = Boolean.TRUE;

    private IBehaviorAnim iBehaviorAnim;


    public Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        minScrollY = ViewConfiguration.get(context).getScaledTouchSlop();//8
        scrollYDistance = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();//50
        iBehaviorAnim = createBehaviorAnim();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        if (!initialize) {
            initialize = iBehaviorAnim.initBehaviorAnimView(coordinatorLayout, child);
        }
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    protected abstract IBehaviorAnim createBehaviorAnim();

    /**
     * 触发滑动嵌套滚动之前调用的方法
     *
     * @param coordinatorLayout coordinatorLayout父布局
     * @param child             使用Behavior的子View
     * @param target            触发滑动嵌套的View(实现NestedScrollingChild接口)
     * @param dx                滑动的X轴距离
     * @param dy                滑动的Y轴距离
     * @param consumed          父布局消费的滑动距离，consumed[0]和consumed[1]代表X和Y方向父布局消费的距离，默认为0
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, ViewCompat.TYPE_TOUCH);

    }

    /**
     * 滑动嵌套滚动时触发的方法
     *
     * @param coordinatorLayout coordinatorLayout父布局
     * @param child             使用Behavior的子View
     * @param target            触发滑动嵌套的View
     * @param dxConsumed        TargetView消费的X轴距离
     * @param dyConsumed        TargetView消费的Y轴距离
     * @param dxUnconsumed      未被TargetView消费的X轴距离
     * @param dyUnconsumed      未被TargetView消费的Y轴距离(如RecyclerView已经到达顶部或底部，而用户继续滑动，此时dyUnconsumed的值不为0，可处理一些越界事件)
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, ViewCompat.TYPE_TOUCH);
        if (!isEnableScroll) {
            return;
        }
        totalScrollY += dyConsumed;
        //如果滑动的Y轴距离小于最小滑动距离 && 总共滑动距离小于最小的滑动距离 return
        if (Math.abs(dyConsumed) < minScrollY && Math.abs(totalScrollY) < scrollYDistance) {
            return;
        }
        //正常滑动
        if (dyConsumed < 0) {
            if (!iBehaviorAnim.isShowing()) {
                iBehaviorAnim.show();
            }
        } else if (dyConsumed > 0) {
            if (iBehaviorAnim.isShowing()) {
                iBehaviorAnim.hide();
            }
        }
        totalScrollY = 0;
    }


    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    public void isEnableScroll(boolean isEnableScroll) {
        this.isEnableScroll = isEnableScroll;
    }

    public IBehaviorAnim getBehaviorAnim() {
        return iBehaviorAnim;
    }
}
