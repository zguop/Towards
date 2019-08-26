package com.waitou.wt_library.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.to.aboomy.statusbar_lib.StatusBarUtil;
import com.to.aboomy.theme_lib.utils.ThemeUtils;
import com.waitou.wt_library.R;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * author   itxp
 * date     2016/7/2 14:04
 * des      baseActivity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable;
    public boolean isImmersiveStatusBar;

    @SuppressWarnings("unchecked")
    public <T extends View> T ff(int id) {
        return (T) getLayoutInflater().inflate(id, null);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T f(int id) {
        return (T) findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T f(View view, int id) {
        return (T) view.findViewById(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isImmersiveStatusBar = immersiveStatusBar();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public boolean immersiveStatusBar() {
        return StatusBarUtil.setStatusBarColor(this, ThemeUtils.getThemeAttrColor(this, R.attr.colorPrimary));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
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

}
