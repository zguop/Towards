package com.waitou.towards.bean;

import android.support.v4.view.PagerAdapter;

import com.waitou.net_library.model.Displayable;

/**
 * Created by waitou on 17/2/15.
 */

public class BannerAdapterInfo implements Displayable {

    public PagerAdapter pagerAdapter;

    public BannerAdapterInfo(PagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;

    }
}
