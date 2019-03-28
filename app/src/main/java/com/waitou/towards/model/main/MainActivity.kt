package com.waitou.towards.model.main

import android.app.ActivityManager
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import android.view.View
import com.to.aboomy.theme_lib.ChangeModeController
import com.umeng.socialize.UMShareAPI
import com.waitou.photo_library.PhotoPickerFinal
import com.waitou.towards.R
import com.waitou.towards.bean.ThemeInfo
import com.waitou.towards.databinding.ActivityMainBinding
import com.waitou.towards.databinding.NavHeaderMainBinding
import com.waitou.towards.model.activity.RecommendedActivity
import com.waitou.towards.model.gallery.GalleryActivity
import com.waitou.towards.model.graffiti.GraffitiActivity
import com.waitou.towards.model.main.fragment.CircleFragment
import com.waitou.towards.model.main.fragment.FigureFragment
import com.waitou.towards.model.main.fragment.PersonFragment
import com.waitou.towards.model.main.fragment.home.HomeFragment
import com.waitou.towards.model.main.fragment.joke.TextJokeFragment
import com.waitou.towards.view.dialog.BaseDialog
import com.waitou.towards.view.dialog.ListOfDialog
import com.waitou.wt_library.base.BaseActivity
import com.waitou.wt_library.base.XFragmentAdapter
import com.waitou.wt_library.recycler.LayoutManagerUtil
import com.waitou.wt_library.recycler.adapter.SingleTypeAdapter
import com.waitou.wt_library.router.Router
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mainBinding: ActivityMainBinding? = null
    private var homeFragment = HomeFragment()
    private var textJokeFragment = TextJokeFragment()
    private var figureFragment = FigureFragment()
    private var circleFragment = CircleFragment()
    private var personFragment = PersonFragment()

    private var mThemeAdapter: SingleTypeAdapter<ThemeInfo>? = null
    private var mThemeDialog: BaseDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ChangeModeController.get().init(this)
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        transparencyBar(mainBinding?.mainDrawerLayout)
        mainBinding?.toolbar?.customTitleView(homeFragment.getHomeToolbar(this))
        mainBinding?.toolbar?.setLeftIcon(R.drawable.icon_menu) { mainBinding?.mainDrawerLayout?.openDrawer(GravityCompat.START) }
        mainBinding?.toolbar?.setRightIcon(R.drawable.svg_ic_qr_scan) { Router.newIntent().from(this).to(RecommendedActivity::class.java).launch() }
        val adapter = XFragmentAdapter(supportFragmentManager, homeFragment, textJokeFragment, figureFragment, circleFragment, personFragment)
        mainBinding?.adapter = adapter
        mainBinding?.mainTab?.setupWithViewPager(mainBinding?.fContent)
        val binding = DataBindingUtil.inflate<NavHeaderMainBinding>(layoutInflater, R.layout.nav_header_main, null, false)
        binding.drawableId = R.drawable.nav_header_img
        mainBinding?.navView?.addHeaderView(binding.root)
        mainBinding?.navView?.setNavigationItemSelectedListener(this)
        mainBinding?.mainTab?.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> mainBinding?.toolbar?.customTitleView(homeFragment.getHomeToolbar(this))
                R.id.menu_joke -> mainBinding?.toolbar?.customTitleView(textJokeFragment.jokeToolBar)
                R.id.menu_circle -> mainBinding?.toolbar?.visibility = View.GONE
            }
            true
        }

    }


    /**
     * 左侧菜单是否被打开
     */
    private fun toggle(): Boolean {
        return mainBinding?.mainDrawerLayout!!.isDrawerOpen(GravityCompat.START)
    }

    override fun onBackPressed() {
        if (toggle()) {
            mainBinding?.mainDrawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            Observable.timer(800, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .subscribe({ killAll() })
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mainBinding?.mainDrawerLayout!!.closeDrawer(GravityCompat.START)
        pend(Observable.timer(200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe {
                    when (item.itemId) {
                        R.id.nav_tuijian -> Router.newIntent().from(this).to(RecommendedActivity::class.java).launch()
                        R.id.nav_all -> {
                        }
                        R.id.nav_meizi -> Router.newIntent().from(this).to(GalleryActivity::class.java).launch()
                        R.id.nav_graffiti -> Router.newIntent().from(this).to(GraffitiActivity::class.java).launch()
                        R.id.nav_collect -> PhotoPickerFinal.get()
                                .with(this)
                                .setStrPhotoList("http://img.hb.aicdn.com/621034b37c53ffc81f5d6a23ae1226d5c67e2b9628267-BYuZLo_fw658", "http://img.hb.aicdn.com/e8cbd6ac1b44cf290debbf1ebcdfac6bdf20487f46146-uz70dU_fw658", "http://img.hb.aicdn.com/0d6adb99906f5dc5107962b8446623ea17b1be4d37679-oKTIYt_fw658", "http://img.hb.aicdn.com/d63edb11718f59390c92b17fe9399215c4f7c96c28643-CXnznk_fw658")
                                .executePreViewPhoto()
                        R.id.nav_theme -> changeNight()
                        R.id.nav_about -> pend(PhotoPickerFinal
                                .get()
                                .with(this)
                                .isMultiMode(true)
                                .setSelectLimit(3)
                                .executePhoto {

                                })
                    }
                })
        return true
    }

    /**
     * 主题更换
     */
    private fun changeNight() {
        if (mThemeAdapter == null) {
            mThemeAdapter = SingleTypeAdapter(this, R.layout.item_theme)
            val themeInfoList = ArrayList<ThemeInfo>()
            val theme = ChangeModeController.get().themeModel
            for (themeModel in ChangeModeController.get().themes) {
                val themeInfo = ThemeInfo()
                themeInfo.themeModule = themeModel
                themeInfo.focus = theme.colorId == themeInfo.themeModule?.colorId
                themeInfoList.add(themeInfo)
            }
            mThemeAdapter!!.set(themeInfoList)
            mThemeAdapter!!.setPresenter(SingleTypeAdapter.Presenter<ThemeInfo> { themeInfo ->
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                    themeInfoList.stream()
                            .filter { info -> info.focus }
                            .forEach { info -> info.focus = false }
                } else {
                    pend(Observable.fromIterable(themeInfoList)
                            .filter { info -> info.focus }
                            .subscribe { info -> info.focus = false })
                }
                themeInfo.focus = true
                ChangeModeController.get().changeNight(this@MainActivity, themeInfo.themeModule)
                mThemeDialog!!.dismiss()
                mThemeDialog = null
            })
        }
        if (mThemeDialog == null) {
            mThemeDialog = ListOfDialog(this)
                    .setLayoutManager(LayoutManagerUtil.getGridLayoutManager(this, 3))
                    .setAdapter(mThemeAdapter)
                    .setTitle("更换主题")
                    .setCancel("取消", null)
        }
        mThemeDialog!!.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }


    override fun onDestroy() {
        super.onDestroy()
        ChangeModeController.get().cancel()
        UMShareAPI.get(this).release()
    }

    /**
     * 杀死进程
     */
    private fun killAll() {
        android.os.Process.killProcess(android.os.Process.myPid())
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        manager.killBackgroundProcesses(packageName)
    }
}
