package com.waitou.towards.model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;
import com.waitou.lib_theme.ChangeModeController;
import com.waitou.lib_theme.ThemeUtils;
import com.waitou.towards.R;
import com.waitou.towards.databinding.ActivityMainBinding;
import com.waitou.towards.databinding.NavHeaderMainBinding;
import com.waitou.towards.model.jokes.activity.RecommendedActivity;
import com.waitou.towards.model.jokes.contract.MainContract;
import com.waitou.towards.model.jokes.fragment.CircleFragment;
import com.waitou.towards.model.jokes.fragment.FigureFragment;
import com.waitou.towards.model.jokes.fragment.HomeFragment;
import com.waitou.towards.model.jokes.fragment.PersonFragment;
import com.waitou.towards.model.jokes.fragment.joke.TextJokeFragment;
import com.waitou.towards.model.presenter.MainPresenter;

import cn.droidlover.xdroid.base.XActivity;
import cn.droidlover.xdroid.base.XFragmentAdapter;


public class MainActivity extends XActivity<MainPresenter, ActivityMainBinding> implements NavigationView.OnNavigationItemSelectedListener, MainContract.MainView {
    private HomeFragment     mHomeFragment;
    private TextJokeFragment mTextJokeFragment;
    private FigureFragment   mFigureFragment;
    private CircleFragment   mCircleFragment;
    private PersonFragment   mPersonFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ChangeModeController.get().init(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(getTextJokeFragment());
    }

    @Override
    public boolean initXView() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getBinding().include.toolbar.initMenuActionBar("首页");
        getBinding().include.toolbar.setBackListener(R.drawable.icon_menu, v -> getBinding().mainDrawerLayout.openDrawer(GravityCompat.START));
        XFragmentAdapter adapter = new XFragmentAdapter(getSupportFragmentManager(), getHomeFragment(), getTextJokeFragment(), getFigureFragment(), getCircleFragment(), getPersonFragment());
        getBinding().setAdapter(adapter);
        getBinding().mainTab.setupWithViewPager(getBinding().fContent);
        getBinding().mainTab.enableShiftingMode(false);
        getBinding().fContent.setOffscreenPageLimit(4);
        NavHeaderMainBinding binding = NavHeaderMainBinding.inflate((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));
        binding.setDrawableId(R.drawable.nav_header_img);
        getBinding().navView.addHeaderView(binding.getRoot());
        getBinding().navView.setNavigationItemSelectedListener(this);
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, getBinding().mainDrawerLayout, ThemeUtils.getThemeAttrColor(this, R.attr.colorPrimary));
        ChangeModeController.get().setOnThemeChangeListener(resourceId -> {
            //主题改版之后，刷新一下bottomBar的状态
            getBinding().mainTab.setItemIconTintList(ThemeUtils.getColorStateList(this, R.color.skin_bottom_bar_not));
            getBinding().mainTab.setItemTextColor(ThemeUtils.getColorStateList(this, R.color.skin_bottom_bar_not));
            getBinding().mainTab.setItemBackgroundResource(ThemeUtils.getAttrTypedValue(this, R.attr.colorPrimary).resourceId);
            StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, getBinding().mainDrawerLayout, ThemeUtils.getThemeAttrColor(this, R.attr.colorPrimary));
        });
        getBinding().mainTab.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    getBinding().include.toolbar.addCustomMenuView(null, 0);
                    break;
                case R.id.menu_joke:
                    getBinding().include.toolbar.addCustomMenuView(getTextJokeFragment().getJokeToolbar(), R.id.menu_joke);
                    break;
            }
            return true;
        });

    }

    @Override
    public void reloadData() {
    }

    @Override
    public void setPresenter(MainPresenter presenter) {
    }

    /**
     * 左侧菜单是否被打开
     */
    private boolean toggle() {
        return getBinding().mainDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (toggle()) {
            getBinding().mainDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_tuijian:
                toActivity(RecommendedActivity.class);
                break;
            case R.id.nav_all:
                toActivity(ThemeActivity.class);
                break;
            case R.id.nav_meizi:
                break;
            case R.id.nav_collect:
                break;
            case R.id.nav_theme:
                break;
            case R.id.nav_about:
                break;
        }
        getBinding().mainDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /*--------------- 初始化fragment ---------------*/
    private HomeFragment getHomeFragment() {
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }
        return mHomeFragment;
    }

    private TextJokeFragment getTextJokeFragment() {
        if (mTextJokeFragment == null) {
            mTextJokeFragment = new TextJokeFragment();
        }
        return mTextJokeFragment;
    }

    private FigureFragment getFigureFragment() {
        if (mFigureFragment == null) {
            mFigureFragment = new FigureFragment();
        }
        return mFigureFragment;
    }

    private CircleFragment getCircleFragment() {
        if (mCircleFragment == null) {
            mCircleFragment = new CircleFragment();
        }
        return mCircleFragment;
    }

    private PersonFragment getPersonFragment() {
        if (mPersonFragment == null) {
            mPersonFragment = new PersonFragment();
        }
        return mPersonFragment;
    }
}
