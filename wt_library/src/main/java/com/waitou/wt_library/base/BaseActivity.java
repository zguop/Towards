package com.waitou.wt_library.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.waitou.wt_library.theme.ChangeModeController;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * author   itxp
 * date     2016/7/2 14:04
 * des      baseActivity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    private VDelegate             mVDelegate;
    private CompositeSubscription mPendingSubscriptions;

    /**
     * 屏幕高宽 子类需复写 isScreenDisplayMetrics 进行获取
     */
    protected int mScreenHeight;
    protected int mScreenWidth;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ChangeModeController.get().setTheme(this);
        super.onCreate(savedInstanceState);
        ActivityUtil.getActivityList().add(this);
        if (isScreenDisplayMetrics()) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            mScreenHeight = displayMetrics.heightPixels;
            mScreenWidth = displayMetrics.widthPixels;
        }
    }

    protected VDelegate getUiDelegate() {
        if (mVDelegate == null) {
            mVDelegate = VDelegateBase.create(this);
        }
        return mVDelegate;
    }

    /**
     * 是否获取屏幕高宽值 可选
     */
    protected boolean isScreenDisplayMetrics() {
        return false;
    }

    /**
     * 向队列中添加一个Subscription
     */
    public void pend(Subscription subscription) {
        if (mPendingSubscriptions == null) {
            mPendingSubscriptions = new CompositeSubscription();
        }
        if (subscription != null) {
            mPendingSubscriptions.add(subscription);
        }
    }

    protected ViewDataBinding bindingInflate(@LayoutRes int resId, ViewGroup container) {
        return DataBindingUtil.inflate(getLayoutInflater(), resId, container, false);
    }

    protected View inflate(@LayoutRes int resId, ViewGroup container) {
        return getLayoutInflater().inflate(resId, container, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUiDelegate().resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getUiDelegate().pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPendingSubscriptions != null && mPendingSubscriptions.hasSubscriptions()) {
            mPendingSubscriptions.clear();
        }
        if (ActivityUtil.getActivityList().contains(this)) {
            ActivityUtil.getActivityList().remove(this);
        }
        getUiDelegate().destroy();
        mVDelegate = null;
    }
}
