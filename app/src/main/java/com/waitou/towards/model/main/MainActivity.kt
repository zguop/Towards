package com.waitou.towards.model.main

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.SkinAppCompatDelegateImpl
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.to.aboomy.statusbar_lib.StatusBarUtil
import com.to.aboomy.theme_lib.annotation.Skinable
import com.to.aboomy.theme_lib.compat.SkinCompatSupportable
import com.to.aboomy.theme_lib.utils.ThemeUtils
import com.umeng.socialize.UMShareAPI
import com.waitou.basic_lib.adapter.BasePagerFragmentAdapter
import com.waitou.imgloader_lib.ImageLoader
import com.waitou.normal_dialog_lib.SheetAdapterDialog
import com.waitou.towards.R
import com.waitou.towards.model.activity.ColorActivity
import com.waitou.towards.model.activity.FlutterActivity
import com.waitou.towards.model.activity.GloadActivity
import com.waitou.towards.model.gallery.GalleryNewActivity
import com.waitou.towards.model.graffiti.GraffitiActivity
import com.waitou.towards.model.main.adater.StylesAdapter
import com.waitou.towards.model.main.fragment.home.HomeNewFragment
import com.waitou.towards.model.main.fragment.joke.JokeFragment
import com.waitou.wt_library.base.BaseActivity
import com.waitou.wt_library.router.Router
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.extensions.CacheImplementation
import kotlinx.android.extensions.ContainerOptions
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

@Skinable
@ContainerOptions(CacheImplementation.SPARSE_ARRAY)
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, SkinCompatSupportable {

    private val homeFragment by lazy { HomeNewFragment() }
    private val jokeFragment by lazy { JokeFragment() }

    override fun immersiveStatusBar(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pageTitleBar.setPadding(0, StatusBarUtil.getStatusBarHeight(this), 0, 0)
        StatusBarUtil.drawerLayoutForColor(this, ContextCompat.getColor(this, R.color.bg_grey), drawerLayout)
        val adapter = BasePagerFragmentAdapter(supportFragmentManager, homeFragment, jokeFragment)
        fContent.offscreenPageLimit = 4
        fContent.adapter = adapter
        mainTab.setupWithViewPager(fContent)
        val navHeaderView = ff<ImageView>(R.layout.nav_header_main)
        ImageLoader.displayImage(navHeaderView, R.drawable.nav_header_img)
        navView.addHeaderView(navHeaderView)
        navView.setNavigationItemSelectedListener(this)
        mainTab.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> pageTitleBar.visibility = View.GONE
                R.id.menu_joke -> pageTitleBar.replaceTitleView(jokeFragment.jokeTitleBar)
//                R.id.menu_figure -> pageTitleBar.restoreTitleView("趣图")
//                R.id.menu_circle -> pageTitleBar.restoreTitleView("圈子")
//                R.id.menu_personal -> pageTitleBar.restoreTitleView("我的")
            }
            true
        }
    }

    override fun getDelegate(): AppCompatDelegate {
        return SkinAppCompatDelegateImpl.get(this, this)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        pend(Observable.timer(200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe {
                    when (item.itemId) {
                        R.id.nav_tuijian -> Router.newIntent().from(this).to(ColorActivity::class.java).launch()
                        R.id.nav_all -> {
                            Router.newIntent().from(this).to(GloadActivity::class.java).launch()

                        }
                        R.id.nav_meizi -> Router.newIntent().from(this).to(GalleryNewActivity::class.java).launch()
                        R.id.nav_graffiti -> Router.newIntent().from(this).to(GraffitiActivity::class.java).launch()
//                        R.mediaId.nav_collect -> PhotoPickerFinal.getInstance()
//                                .with(this)
//                                .setStrPhotoList("http://img.hb.aicdn.com/621034b37c53ffc81f5d6a23ae1226d5c67e2b9628267-BYuZLo_fw658", "http://img.hb.aicdn.com/e8cbd6ac1b44cf290debbf1ebcdfac6bdf20487f46146-uz70dU_fw658", "http://img.hb.aicdn.com/0d6adb99906f5dc5107962b8446623ea17b1be4d37679-oKTIYt_fw658", "http://img.hb.aicdn.com/d63edb11718f59390c92b17fe9399215c4f7c96c28643-CXnznk_fw658")
//                                .executePreViewPhoto()
                        R.id.nav_theme -> changeNight()
                        R.id.nav_about -> startActivity(Intent(this, FlutterActivity::class.java))
                    }
                })
        return true
    }

    override fun applySkin() {
        StatusBarUtil.drawerLayoutForColor(this, ContextCompat.getColor(this, R.color.bg_grey), drawerLayout)
        mainTab.itemIconTintList = ThemeUtils.getColorStateList(this, R.color.skin_bottom_bar_not)
        mainTab.itemTextColor = ThemeUtils.getColorStateList(this, R.color.skin_bottom_bar_not)
        homeFragment.applySkin()
    }

    /**
     * 主题更换
     */
    private fun changeNight() {
        drawerLayout.closeDrawer(GravityCompat.START)
        val adapter = StylesAdapter(this)
        val dialog = SheetAdapterDialog()
                .grid(3)
                .setTitle("更换主题")
                .setRecyclerAdapter(adapter)
                .setGravity(Gravity.CENTER)
        dialog.show(supportFragmentManager)
        adapter.call = {
            dialog.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        UMShareAPI.get(this).release()
        killAll()
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
