package com.waitou.towards.bean

import android.databinding.BaseObservable
import android.databinding.Bindable

import com.waitou.towards.BR
import com.waitou.wt_library.theme.ThemeEnum

/**
 * Created by waitou on 17/2/8.
 */

class ThemeInfo : BaseObservable() {

    var themeEnum: ThemeEnum? = null

    @Bindable
    var focus: Boolean = false
        set(value) {
            //focus = value相当于set(value)
            field = value
            notifyPropertyChanged(BR.focus)
        }
}
