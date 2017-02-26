package com.waitou.wt_library.base;

import android.content.Context;
import android.view.View;

import com.waitou.wt_library.imageloader.ILFactory;

/**
 * Created by wanglei on 2016/12/1.
 */

public class VDelegateBase implements VDelegate {

    public Context context;

    private VDelegateBase(Context context) {
        this.context = context;
    }

    public static VDelegateBase create(Context context) {
        return new VDelegateBase(context);
    }

    @Override
    public void resume() {
        ILFactory.getLoader().resume(context);
    }

    @Override
    public void pause() {
        ILFactory.getLoader().pause(context);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void flagVisible(boolean flag, View view) {
        if (flag) view.setVisibility(View.VISIBLE);
    }

    @Override
    public void visible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void flagGone(boolean flag, View view) {
        if (flag) view.setVisibility(View.GONE);
    }

    @Override
    public void gone(View view) {
        view.setVisibility(View.GONE);
    }

    @Override
    public void inVisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }
}
