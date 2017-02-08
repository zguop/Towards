package com.waitou.towards.model.jokes.contract;

import com.waitou.towards.bean.ThemeInfo;
import com.waitou.towards.model.jokes.activity.ThemeActivity;

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
