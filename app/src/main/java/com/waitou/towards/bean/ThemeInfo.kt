package com.waitou.towards.bean

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.to.aboomy.theme_lib.config.ThemeModule
import com.waitou.towards.BR

/**
 * Created by waitou on 17/2/8.
 */

class ThemeInfo : BaseObservable() {

    var themeModule: ThemeModule? = null

    @Bindable
    var focus: Boolean = false
        set(value) {
            //focus = value相当于set(value)
            field = value
            notifyPropertyChanged(BR.focus)
        }
}
