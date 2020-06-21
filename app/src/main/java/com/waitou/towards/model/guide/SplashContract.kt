package com.waitou.towards.model.guide

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

/**
 * auth aboom
 * date 2019-07-10
 */
interface SplashContract {
    interface SplashView {
        fun initImageResource(resource: Drawable)
        fun getImgDrawable(@DrawableRes resId: Int): Drawable
    }
    interface SplashPresenter {
        fun setLogoImg()
        fun onDestroy()
    }
}
