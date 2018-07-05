package com.waitou.towards.model.main.fragment.joke;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.to.aboomy.theme_lib.config.ThemeUtils;
import com.waitou.towards.R;
import com.waitou.towards.databinding.IncludeViewPagerBinding;
import com.waitou.towards.databinding.ToolbarJokeTitleBinding;
import com.waitou.wt_library.base.XFragment;
import com.waitou.wt_library.base.XFragmentAdapter;

/**
 * Created by waitou on 16/12/23.
 * 段子
 */

public class TextJokeFragment extends XFragment<TextJokePresenter, IncludeViewPagerBinding> {

    private ToolbarJokeTitleBinding mToolbarJokeTitle;

    @Override
    public TextJokePresenter createPresenter() {
        return new TextJokePresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.include_view_pager;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initTootBar();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { //6.0以下xml中默认颜色 高亮 代码重新设置一遍
            mToolbarJokeTitle.item1.setTextColor(ThemeUtils.getColorStateList(getActivity(), R.color.skin_general_text_not));
            mToolbarJokeTitle.item2.setTextColor(ThemeUtils.getColorStateList(getActivity(), R.color.skin_general_text_not));
        }
        XFragmentAdapter adapter = new XFragmentAdapter(getChildFragmentManager(), getP().getFragment(0), getP().getFragment(1));
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

    public ViewDataBinding initTootBar() {
        if (mToolbarJokeTitle == null) {
            mToolbarJokeTitle = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.toolbar_joke_title, null, false);
        }
        return mToolbarJokeTitle;
    }

    public ViewDataBinding getJokeToolBar() {
        return initTootBar();
    }
}





