package com.waitou.towards.model.presenter;

import com.github.markzhai.recyclerview.BaseViewAdapter;
import com.waitou.lib_theme.ChangeModeController;
import com.waitou.lib_theme.ThemeModel;
import com.waitou.towards.bean.ThemeInfo;
import com.waitou.towards.model.jokes.activity.ThemeActivity;
import com.waitou.towards.model.jokes.contract.ThemeContract;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroid.base.XPresent;

/**
 * Created by waitou on 17/2/8.
 */

public class ThemePresenter extends XPresent<ThemeActivity> implements ThemeContract.ThemePresenter, BaseViewAdapter.Presenter {

    private List<ThemeInfo> themeInfoList = new ArrayList<>();

    @Override
    public void start() {
    }

    @Override
    public List<ThemeInfo> loadData() {
        if (themeInfoList.size() > 0) {
            return themeInfoList;
        }
        ThemeModel theme = ChangeModeController.get().getThemeModel();
        for (ThemeModel themeModel : ThemeModel.values()) {
            ThemeInfo themeInfo = new ThemeInfo();
            themeInfo.themeModel = themeModel;
            themeInfo.focus = theme.getColorId() == themeInfo.themeModel.getColorId();
            themeInfoList.add(themeInfo);
        }
        return themeInfoList;
    }

    @Override
    public void onItemClick(ThemeInfo info) {
        for (int i = 0; i < themeInfoList.size(); i++) {
            if (themeInfoList.get(i).focus) {
                themeInfoList.get(i).setFocus(false);
            }
        }
        info.setFocus(true);
        getV().theme(info);
    }
}
