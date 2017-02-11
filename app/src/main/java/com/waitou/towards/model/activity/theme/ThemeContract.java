package com.waitou.towards.model.activity.theme;

import com.waitou.towards.bean.ThemeInfo;

import java.util.List;

import cn.droidlover.xdroid.base.UIPresent;

/**
 * Created by waitou on 17/2/8.
 */

public interface ThemeContract {


    interface ThemePresenter extends UIPresent<ThemeActivity> {
        List<ThemeInfo> loadData();
        void onItemClick(ThemeInfo model);
    }


}
