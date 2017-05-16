package com.waitou.towards.model.main;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;
import com.waitou.photo_library.PhotoPickerFinal;
import com.waitou.three_library.share.UShare;
import com.waitou.towards.R;
import com.waitou.towards.bean.ThemeInfo;
import com.waitou.towards.databinding.ActivityMainBinding;
import com.waitou.towards.databinding.NavHeaderMainBinding;
import com.waitou.towards.model.activity.RecommendedActivity;
import com.waitou.towards.model.gallery.GalleryActivity;
import com.waitou.towards.model.graffiti.GraffitiActivity;
import com.waitou.towards.model.main.fragment.CircleFragment;
import com.waitou.towards.model.main.fragment.FigureFragment;
import com.waitou.towards.model.main.fragment.PersonFragment;
import com.waitou.towards.model.main.fragment.home.HomeFragment;
import com.waitou.towards.model.main.fragment.joke.TextJokeFragment;
import com.waitou.towards.view.dialog.BaseDialog;
import com.waitou.towards.view.dialog.ListOfDialog;
import com.waitou.wt_library.base.XActivity;
import com.waitou.wt_library.base.XFragmentAdapter;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.recycler.LayoutManagerUtli;
import com.waitou.wt_library.recycler.adapter.SingleTypeAdapter;
import com.waitou.wt_library.router.Router;
import com.waitou.wt_library.theme.ChangeModeController;
import com.waitou.wt_library.theme.ThemeEnum;
import com.waitou.wt_library.theme.ThemeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static android.os.Build.VERSION_CODES.N;


public class MainActivity extends XActivity<XPresent, ActivityMainBinding> implements NavigationView.OnNavigationItemSelectedListener {

    private HomeFragment                 mHomeFragment;
    private TextJokeFragment             mTextJokeFragment;
    private FigureFragment               mFigureFragment;
    private CircleFragment               mCircleFragment;
    private PersonFragment               mPersonFragment;
    private SingleTypeAdapter<ThemeInfo> mThemeAdapter;
    private BaseDialog                   mThemeDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ChangeModeController.get().init(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean defaultXView() {
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
        NavHeaderMainBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_main, null, false);
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
        getBinding().mainDrawerLayout.closeDrawer(GravityCompat.START);
        pend(Observable.timer(200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
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
                            Router.newIntent().from(this).to(GraffitiActivity.class).launch();
                            break;
                        case R.id.nav_collect:
                            PhotoPickerFinal.get()
                                    .with(this)
                                    .setStrPhotoList("http://img.hb.aicdn.com/621034b37c53ffc81f5d6a23ae1226d5c67e2b9628267-BYuZLo_fw658"
                                            , "http://img.hb.aicdn.com/e8cbd6ac1b44cf290debbf1ebcdfac6bdf20487f46146-uz70dU_fw658"
                                            , "http://img.hb.aicdn.com/0d6adb99906f5dc5107962b8446623ea17b1be4d37679-oKTIYt_fw658"
                                            , "http://img.hb.aicdn.com/d63edb11718f59390c92b17fe9399215c4f7c96c28643-CXnznk_fw658")
                                    .executePreViewPhoto();
                            break;
                        case R.id.nav_theme:
                            changeNight();
                            break;
                        case R.id.nav_about:
                            pend(PhotoPickerFinal
                                    .get()
                                    .isMultiMode(true)
                                    .setSelectLimit(3)
                                    .executePhoto(photoInfos -> {

                                    }));
                            break;
                    }
                }));
        return true;
    }

    /**
     * 主题更换
     */
    private void changeNight() {
        if (mThemeAdapter == null) {
            mThemeAdapter = new SingleTypeAdapter<>(this, R.layout.item_theme);
            List<ThemeInfo> themeInfoList = new ArrayList<>();
            ThemeEnum theme = ChangeModeController.get().getThemeModel();
            for (ThemeEnum themeModel : ThemeEnum.values()) {
                ThemeInfo themeInfo = new ThemeInfo();
                themeInfo.themeEnum = themeModel;
                themeInfo.focus = theme.getColorId() == themeInfo.themeEnum.getColorId();
                themeInfoList.add(themeInfo);
            }
            mThemeAdapter.set(themeInfoList);
            mThemeAdapter.setPresenter((SingleTypeAdapter.Presenter<ThemeInfo>) themeInfo -> {
                if (Build.VERSION.SDK_INT > N) {
                    themeInfoList.stream()
                            .filter(info -> info.focus)
                            .forEach(info -> info.setFocus(false));
                } else {
                    pend(Observable.fromIterable(themeInfoList)
                            .filter(info -> info.focus)
                            .subscribe(info -> info.setFocus(false)));
                }
                themeInfo.setFocus(true);
                ChangeModeController.get().changeNight(MainActivity.this, themeInfo.themeEnum);
                mThemeDialog.dismiss();
                mThemeDialog = null;
            });
        }
        if (mThemeDialog == null) {
            mThemeDialog = new ListOfDialog(this)
                    .setLayoutManager(LayoutManagerUtli.getGridLayoutManager(this, 3))
                    .setAdapter(mThemeAdapter)
                    .setTitle("更换主题")
                    .setCancel("取消", null);
        }
        mThemeDialog.show();
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
        ChangeModeController.get().cancel();
        super.onDestroy();
    }
}
