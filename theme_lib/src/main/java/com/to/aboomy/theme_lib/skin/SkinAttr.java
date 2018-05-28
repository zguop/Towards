package com.to.aboomy.theme_lib.skin;

import android.view.View;

import com.to.aboomy.theme_lib.ChangeModeController;

/**
 * Created by waitou on 17/1/17.
 * 装载更该主题改变的元素
 */

public class SkinAttr {

    private String attrName;
    private String attrValueResName;
    private String attrValueTypeName;


    private SkinAttrType attrType;

    public SkinAttr(SkinAttrType attrType, String attrName, String attrValueResName, String attrValueTypeName) {
        this.attrType = attrType;
        this.attrName = attrName;
        this.attrValueResName = attrValueResName;
        this.attrValueTypeName = attrValueTypeName;
    }

    public void apply(View view) {
        attrType.apply(view, attrName, attrValueResName, attrValueTypeName);
    }
}
