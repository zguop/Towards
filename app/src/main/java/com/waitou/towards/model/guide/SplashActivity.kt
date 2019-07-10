package com.waitou.towards.model.guide

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.ActivityCompat
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.blankj.utilcode.util.ScreenUtils
import com.waitou.towards.R
import com.waitou.towards.model.main.MainActivity
import com.waitou.wt_library.base.BaseActivity
import com.waitou.wt_library.router.Router
import kotlinx.android.synthetic.main.activity_logo.*


/**
 * Created by waitou on 17/2/3.
 * 启动页
 */

class SplashActivity : BaseActivity(), SplashContract.SplashView {

    private val presenter: SplashContract.SplashPresenter  by lazy { SplashPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)
        ScreenUtils.setFullScreen(this)
        presenter.setLogoImg()
    }

    override fun initImageResource(resource: Drawable) {
        logoIv.setImageDrawable(resource)
        logoIv.postDelayed({
            val animation = AnimationUtils.loadAnimation(this, R.anim.splash)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) { navigateToMain() }
            })
            logoIv.startAnimation(animation)
        }, 100)
    }

    override fun getImgDrawable(@DrawableRes resId: Int): Drawable {
        return ActivityCompat.getDrawable(this, resId)!!
    }

    private fun navigateToMain() {
        Router.newIntent().from(this).to(MainActivity::class.java).finish().launch()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}