package com.waitou.wt_library.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.to.aboomy.theme_lib.ChangeModeController;
import com.waitou.wt_library.kit.UActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * author   itxp
 * date     2016/7/2 14:04
 * des      baseActivity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    private VDelegate           mVDelegate;
    private CompositeDisposable mCompositeDisposable;


    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ChangeModeController.get().setTheme(this);
        super.onCreate(savedInstanceState);
        UActivity.getActivityList().add(this);
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
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        if (UActivity.getActivityList().contains(this)) {
            UActivity.getActivityList().remove(this);
        }
        getUiDelegate().destroy();
        mVDelegate = null;
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
    public void pend(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        if (disposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }



    @Override
    public void onBackPressed() {
        if (onBackPressedOverride()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 子类处理返回逻辑的时候，最好不要重写onBackPressed。替代的重写该方法。
     * 但是子类点击back的时候如果不返回，则可以重写onBackPressed。
     *
     * @return true:onBackPressed被占用。false：不占用onBackPressed。
     */
    protected boolean onBackPressedOverride() {
        return false;
    }

    protected ViewDataBinding bindingInflate(@LayoutRes int resId, ViewGroup container) {
        return DataBindingUtil.inflate(getLayoutInflater(), resId, container, false);
    }

    protected View inflate(@LayoutRes int resId, ViewGroup container) {
        return getLayoutInflater().inflate(resId, container, false);
    }
}
