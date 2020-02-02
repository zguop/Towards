package com.to.aboomy.recycler_lib.species;


import android.support.annotation.LayoutRes;

import com.to.aboomy.recycler_lib.adapter.Displayable;

/**
 * auth aboom
 * date 2019-11-07
 */
public class Show implements Displayable {

    @LayoutRes
    private int layoutId;

    public Show(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
    }

    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public Class[] getHolderClass() {
        return new Class[]{ShowDelegate.class};
    }
}
