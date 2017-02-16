package com.waitou.towards.model.main.fragment.joke;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.waitou.towards.R;
import com.waitou.towards.databinding.IncludeViewPagerBinding;
import com.waitou.towards.databinding.ToolbarJokeTitleBinding;
import com.waitou.towards.model.main.contract.MainContract;
import com.waitou.wt_library.base.XFragment;
import com.waitou.wt_library.base.XFragmentAdapter;
import com.waitou.wt_library.theme.ThemeUtils;

import java.util.List;

/**
 * Created by waitou on 16/12/23.
 * 段子
 */

public class TextJokeFragment extends XFragment<MainContract.MainPresenter, IncludeViewPagerBinding> implements MainContract.TextJokeView {

    private MainContract.MainPresenter mPresenter;
    private ToolbarJokeTitleBinding    mToolbarJokeTitle;

    @Override
    public boolean initXView() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return R.layout.include_view_pager;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initTootBar();
        List<Fragment> jokeFragmentList = mPresenter.getJokeFragmentList();
        if (Build.VERSION.SDK_INT < 23) { //6.0以下xml中默认颜色 高亮 原因未知 代码重新设置一遍
            mToolbarJokeTitle.item1.setTextColor(ThemeUtils.getColorStateList(getActivity(), R.color.skin_joke_title_not));
            mToolbarJokeTitle.item2.setTextColor(ThemeUtils.getColorStateList(getActivity(), R.color.skin_joke_title_not));
        }
        XFragmentAdapter adapter = new XFragmentAdapter(getChildFragmentManager(), jokeFragmentList, null);
        getBinding().setAdapter(adapter);
        getBinding().viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mToolbarJokeTitle.item1.setChecked(true);
                        break;
                    case 1:
                        mToolbarJokeTitle.item2.setChecked(true);
                        break;
                }
            }
        });
        mToolbarJokeTitle.group.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.item1:
                    getBinding().viewPager.setCurrentItem(0);
                    break;
                case R.id.item2:
                    getBinding().viewPager.setCurrentItem(1);
                    break;
            }
        });
    }

    @Override
    public void reloadData() {
    }

    @Override
    public JokeContentFragment getCurrentJokeFragment() {
        return (JokeContentFragment) mPresenter.getJokeFragmentList().get(getBinding().viewPager.getCurrentItem());
    }

    public ViewDataBinding initTootBar() {
        if (mToolbarJokeTitle == null) {
            mToolbarJokeTitle = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.toolbar_joke_title, null, false);
        }
        return mToolbarJokeTitle;
    }

    public ViewDataBinding getJokeToolBar() {
        return initTootBar();
    }

    @Override
    public void setPresenter(MainContract.MainPresenter presenter) {
        this.mPresenter = presenter;
    }
}





