package com.waitou.basic_lib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wanglei on 2016/12/10.
 */

public class BasePagerFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles;

    public BasePagerFragmentAdapter(FragmentManager fm, Fragment... fragment) {
        super(fm);
        this.fragmentList.clear();
        Collections.addAll(fragmentList, fragment);
    }

    public BasePagerFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
        super(fm);
        this.fragmentList.clear();
        this.fragmentList.addAll(fragmentList);
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > position) {
            return titles[position];
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
