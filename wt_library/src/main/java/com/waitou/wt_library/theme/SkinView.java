package com.waitou.wt_library.theme;

import android.view.View;

import java.util.List;

/**
 * Created by waitou on 17/1/17.
 * 装载改变主题的view 及 需要改变的主题元素
 */

public class SkinView {
    private View           view;
    private List<SkinAttr> attrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.view = view;
        this.attrs = skinAttrs;
    }

    public void apply() {
        if (view == null) {
            return;
        }
        if (view instanceof SkinCompatSupportable) {
            ((SkinCompatSupportable) view).applySkin();
        }
        if (attrs == null) {
            return;
        }
        for (SkinAttr attr : attrs) {
            attr.apply(view);
        }
    }
}
