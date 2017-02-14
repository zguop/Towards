package cn.droidlover.xdroid.theme;

import android.view.View;

/**
 * Created by waitou on 17/1/17.
 * 装载更该主题改变的元素
 */

public class SkinAttr {

    private String                            resName;
    private ChangeModeController.SkinAttrType attrType;

    public SkinAttr(ChangeModeController.SkinAttrType attrType, String resName) {
        this.resName = resName;
        this.attrType = attrType;
    }

    public void apply(View view, int theme) {
        attrType.apply(view, resName,theme);
    }
}
