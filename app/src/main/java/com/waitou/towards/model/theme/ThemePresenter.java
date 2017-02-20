package com.waitou.towards.model.theme;

import com.waitou.towards.bean.ThemeInfo;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.recycler.adapter.BaseViewAdapter;
import com.waitou.wt_library.theme.ChangeModeController;
import com.waitou.wt_library.theme.ThemeEnum;

import java.util.ArrayList;
import java.util.List;


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
        ThemeEnum theme = ChangeModeController.get().getThemeModel();
        for (ThemeEnum themeModel : ThemeEnum.values()) {
            ThemeInfo themeInfo = new ThemeInfo();
            themeInfo.themeEnum = themeModel;
            themeInfo.focus = theme.getColorId() == themeInfo.themeEnum.getColorId();
            themeInfoList.add(themeInfo);
        }
        return themeInfoList;
    }

    @Override
    public void onItemClick(ThemeInfo info, int position) {
        for (int i = 0; i < themeInfoList.size(); i++) {
            if (themeInfoList.get(i).focus) {
                themeInfoList.get(i).setFocus(false);
            }
        }
        info.setFocus(true);
        getV().theme(info);
    }
}
