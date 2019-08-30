package com.waitou.towards.model.main.adater

import android.content.Context
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.to.aboomy.theme_lib.SkinModeController
import com.to.aboomy.theme_lib.utils.ThemeUtils
import com.waitou.towards.R
import com.waitou.towards.bean.StylesInfo
import kotlinx.android.synthetic.main.item_theme.view.*

/**
 * auth aboom
 * date 2019-08-23
 */
class StylesAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var stylesColor = ThemeUtils.getColorPrimary(context)

    private val listOf: List<StylesInfo> = listOf(
            StylesInfo(R.style.AppThemeRed, R.color.colorRed),
            StylesInfo(R.style.AppThemeBlue, R.color.colorBlue),
            StylesInfo(R.style.AppThemePowder, R.color.colorPowder)
    )

    var call: (() -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder =
            StylesViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_theme, p0, false))

    override fun getItemCount(): Int = listOf.size

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val stylesInfo = listOf[p1]
        p0.itemView.themeItem.setColor(stylesInfo.colorId)
        val color = ActivityCompat.getColor(context, stylesInfo.colorId)
        p0.itemView.themeItemFocus.visibility = if (stylesColor == color) View.VISIBLE else View.GONE
        p0.itemView.setOnClickListener {
            stylesColor = color
            notifyDataSetChanged()
            SkinModeController.getInstance().changeSkin(stylesInfo.themeId)
            call?.invoke()
        }
    }

    private class StylesViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
}
