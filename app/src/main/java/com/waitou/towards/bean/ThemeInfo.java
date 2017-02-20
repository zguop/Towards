package com.waitou.towards.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.waitou.towards.BR;
import com.waitou.wt_library.theme.ThemeEnum;

/**
 * Created by waitou on 17/2/8.
 */

public class ThemeInfo extends BaseObservable {

    public ThemeEnum themeEnum;

    @Bindable
    public boolean focus;

    public void setFocus(boolean focus) {
        this.focus = focus;
        notifyPropertyChanged(BR.focus);
    }

}
