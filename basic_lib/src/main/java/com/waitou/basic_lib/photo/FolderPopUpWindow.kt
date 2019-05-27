package com.waitou.basic_lib.photo

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
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
        animationStyle = R.style.dl_anim_slide_share_from_bottom
        width = ViewGroup.LayoutParams.MATCH_PARENT  //如果不设置，就是 AnchorView 的宽度
        height = ViewGroup.LayoutParams.MATCH_PARENT
        setBackgroundDrawable(ColorDrawable(0))
    }
}
