package com.waitou.basic_lib.photo.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.TRANSLATION_Y
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.PopupWindow
import com.waitou.basic_lib.R
import kotlinx.android.synthetic.main.bs_pop_albums.view.*

/**
 * auth aboom
 * date 2019-05-25
 */
class FolderPopUpWindow(context: Context, adapter: RecyclerView.Adapter<*>) : PopupWindow(context) {
    init {
        contentView = View.inflate(context, R.layout.bs_pop_albums, null)
        contentView.popList.layoutManager = LinearLayoutManager(context)
        contentView.popList.adapter = adapter
        isOutsideTouchable = true
        animationStyle = R.style.bs_pop_style_fade
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.MATCH_PARENT
        setBackgroundDrawable(ColorDrawable(0))
        contentView.mask.setOnClickListener { dismiss() }
        contentView.margin.setOnClickListener { dismiss() }
    }

    override fun showAtLocation(parent: View?, gravity: Int, x: Int, y: Int) {
        super.showAtLocation(parent, gravity, x, y)
        contentView.popList.post {
            val maxHeight = (Resources.getSystem().displayMetrics.density * 300 + .5f).toInt()
            val height = contentView.popList.height
            if (height > maxHeight) {
                val layoutParams = contentView.popList.layoutParams
                layoutParams.height = maxHeight
                contentView.popList.layoutParams = layoutParams
            }
            val animator = ObjectAnimator.ofFloat(contentView.popList, TRANSLATION_Y, contentView.popList.height.toFloat(), 0f)
            animator.duration = 400
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.start()
        }
    }

    override fun dismiss() {
        val animator = ObjectAnimator.ofFloat(contentView.popList, TRANSLATION_Y, 0f, contentView.popList.height.toFloat())
        animator.duration = 300
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super@FolderPopUpWindow.dismiss()
            }
        })
        animator.start()
    }
}
