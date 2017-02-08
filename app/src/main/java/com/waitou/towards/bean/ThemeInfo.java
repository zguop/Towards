package com.waitou.towards.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.waitou.lib_theme.ThemeModel;
import com.waitou.towards.BR;

/**
 * Created by waitou on 17/2/8.
 */

public class ThemeInfo extends BaseObservable {

    public ThemeModel themeModel;

    @Bindable
    public boolean focus;


    public void setFocus(boolean focus) {
        this.focus = focus;
        notifyPropertyChanged(BR.focus);
    }

}
