package com.waitou.towards.model.activity.theme;

import com.waitou.towards.bean.ThemeInfo;
import com.waitou.wt_library.base.UIPresent;

import java.util.List;


/**
 * Created by waitou on 17/2/8.
 */

public interface ThemeContract {


    interface ThemePresenter extends UIPresent<ThemeActivity> {
        List<ThemeInfo> loadData();
        void onItemClick(ThemeInfo model);
    }


}
