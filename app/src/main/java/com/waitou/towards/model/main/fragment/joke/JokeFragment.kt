package com.waitou.towards.model.main.fragment.joke

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.waitou.basic_lib.adapter.BasePagerFragmentAdapter
import com.waitou.towards.R
import com.waitou.wt_library.base.LazyFragment
import kotlinx.android.synthetic.main.include_view_pager.*
import kotlinx.android.synthetic.main.toolbar_joke_title.view.*

/**
 * auth aboom
 * date 2019-05-19
 */
class JokeFragment : LazyFragment() {

    val jokeTitleBar: View by lazy {
        View.inflate(activity, R.layout.toolbar_joke_title, null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.include_view_pager, container, false)
    }

    /**
     * descendantFocusability
     *     FOCUS_BEFORE_DESCENDANTS:viewGroup会优先其子类控件而获取到焦点
     *     FOCUS_AFTER_DESCENDANTS:viewGroup只有当其子类控件不需要获取焦点时才获取焦点
     *     FOCUS_BLOCK_DESCENDANTS:viewGroup会覆盖子类控件而直接获得焦点
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
        val adapter = BasePagerFragmentAdapter(childFragmentManager, JokeLotFragment.newInstance(0), JokeLotFragment.newInstance(1))
        viewPager.adapter = adapter
        jokeTitleBar.group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.item1 -> viewPager.currentItem = 0
                R.id.item2 -> viewPager.currentItem = 1
            }
        }
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> jokeTitleBar.group.check(R.id.item1)
                    1 -> jokeTitleBar.group.check(R.id.item2)
                }
            }
        })
    }

    fun applySkin() {


    }
}
