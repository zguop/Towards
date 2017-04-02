package com.waitou.wt_library.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.waitou.wt_library.kit.UActivity;
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

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ChangeModeController.get().setTheme(this);
        super.onCreate(savedInstanceState);
        UActivity.getActivityList().add(this);
    }

    protected VDelegate getUiDelegate() {
        if (mVDelegate == null) {
            mVDelegate = VDelegateBase.create(this);
        }
        return mVDelegate;
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
        if (UActivity.getActivityList().contains(this)) {
            UActivity.getActivityList().remove(this);
        }
        getUiDelegate().destroy();
        mVDelegate = null;
    }
}
