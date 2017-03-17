package com.waitou.towards.model.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;
import com.waitou.towards.R;
import com.waitou.towards.databinding.ActivityMainBinding;
import com.waitou.towards.databinding.NavHeaderMainBinding;
import com.waitou.towards.model.activity.RecommendedActivity;
import com.waitou.towards.model.event.ThemeEvent;
import com.waitou.towards.model.gallery.GalleryActivity;
import com.waitou.towards.model.main.fragment.CircleFragment;
import com.waitou.towards.model.main.fragment.FigureFragment;
import com.waitou.towards.model.main.fragment.PersonFragment;
import com.waitou.towards.model.main.fragment.home.HomeFragment;
import com.waitou.towards.model.main.fragment.joke.TextJokeFragment;
import com.waitou.towards.model.theme.ThemeActivity;
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.base.XFragmentAdapter;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.router.Router;
import com.waitou.wt_library.rx.RxBus;
import com.waitou.wt_library.theme.ChangeModeController;
import com.waitou.wt_library.theme.ThemeUtils;


public class MainActivity extends XActivity<XPresent, ActivityMainBinding> implements NavigationView.OnNavigationItemSelectedListener {

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
    public boolean initXView() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getBinding().toolbar.fromCustomMenuView(getHomeFragment().getHomeToolbar(this), R.id.home);
        getBinding().toolbar.setBackListener(R.drawable.icon_menu, v -> getBinding().mainDrawerLayout.openDrawer(GravityCompat.START));
        XFragmentAdapter adapter = new XFragmentAdapter(getSupportFragmentManager(), getHomeFragment(), getTextJokeFragment(), getFigureFragment(), getCircleFragment(), getPersonFragment());
        getBinding().setAdapter(adapter);
        getBinding().mainTab.setupWithViewPager(getBinding().fContent);
        NavHeaderMainBinding binding = NavHeaderMainBinding.inflate((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));
        binding.setDrawableId(R.drawable.nav_header_img);
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, getBinding().mainDrawerLayout, ThemeUtils.getThemeAttrColor(this, R.attr.colorPrimary));
        getBinding().navView.addHeaderView(binding.getRoot());
        getBinding().navView.setNavigationItemSelectedListener(this);
        getBinding().mainTab.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    getBinding().toolbar.fromCustomMenuView(getHomeFragment().getHomeToolbar(this), R.id.home);
                    break;
                case R.id.menu_joke:
                    getBinding().toolbar.fromCustomMenuView(getTextJokeFragment().getJokeToolBar(), R.id.menu_joke);
                    break;
                case R.id.menu_circle:
                    getUiDelegate().gone(getBinding().toolbar);
                    break;
            }
            return true;
        });
        pend(RxBus.getDefault().toObservable(ThemeEvent.class).
                subscribe(event -> {
                    if (event.getInfo() != null) {
                        ChangeModeController.get().changeNight(MainActivity.this, event.getInfo().themeEnum);
                    }
                }));
    }

    @Override
    public void reloadData() {
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
                Router.newIntent().from(this).to(RecommendedActivity.class).launch();
                break;
            case R.id.nav_all:
                break;
            case R.id.nav_meizi:
                Router.newIntent().from(this).to(GalleryActivity.class).launch();
                break;
            case R.id.nav_graffiti:
                break;
            case R.id.nav_collect:
                break;
            case R.id.nav_theme:
                Router.newIntent().from(this).to(ThemeActivity.class).launch();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChangeModeController.get().cancel();
    }
}
