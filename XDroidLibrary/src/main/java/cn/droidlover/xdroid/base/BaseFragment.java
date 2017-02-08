package cn.droidlover.xdroid.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * author   itxp
 * date     2016/7/2 17:11
 * des      fragment 基类 ,可扩展
 */
public abstract class BaseFragment extends Fragment {

    private CompositeSubscription mPendingSubscriptions;

    /**
     * 屏幕高宽 子类需复写 isScreenDisplayMetrics 进行获取
     */
    protected int mScreenHeight;
    protected int mScreenWidth;

    /**
     * 控件是否初始化完毕
     */
    protected boolean isViewCreated;

    /**
     * 数据是否已经加载完毕
     */
    protected boolean isLoadDataCompleted;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isScreenDisplayMetrics()) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            mScreenHeight = displayMetrics.heightPixels;
            mScreenWidth = displayMetrics.widthPixels;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && isViewCreated && !isLoadDataCompleted) {
            if (fragmentVisibleHint()) {
                isLoadDataCompleted = true;
            }
        }
    }

    /**
     * fragment的懒加载
     */
    protected boolean fragmentVisibleHint() {
        return false;
    }

    /**
     * 是否获取屏幕高宽值 可选
     */
    protected boolean isScreenDisplayMetrics() {
        return false;
    }

    /**
     * 向队列中添加一个 subscription
     */
    public void pend(Subscription subscription) {
        if (mPendingSubscriptions == null) {
            mPendingSubscriptions = new CompositeSubscription();
        }
        if (subscription != null) {
            mPendingSubscriptions.add(subscription);
        }
    }

    /**
     * 页面跳转方法
     */
    protected void gotoActivity(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * 绑定bundle数据页面跳转方法
     */
    protected void gotoActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPendingSubscriptions != null && mPendingSubscriptions.hasSubscriptions()) {
            mPendingSubscriptions.clear();
        }
    }
}
