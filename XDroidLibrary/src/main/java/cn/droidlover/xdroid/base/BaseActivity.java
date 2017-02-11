package cn.droidlover.xdroid.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.waitou.lib_theme.ChangeModeController;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * author   itxp
 * date     2016/7/2 14:04
 * des      baseActivity基类,可扩展
 */
public abstract class BaseActivity extends AppCompatActivity {

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

    /**
     * 页面跳转方法
     */
    protected void toActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 绑定bundle数据页面跳转方法
     */
    protected void toActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 页面跳转方法,并关闭当前activity
     */
    protected void toFinishActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * 绑定bundle数据页面跳转方法,并关闭当前activity
     */
    protected void toFinishActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * 页面跳转方法, requestCode回调
     */
    protected void toActivityForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 页面跳转方法, requestCode回调 绑定bundle数据
     */
    protected void toActivityForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
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
    }


}
