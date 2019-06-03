package com.waitou.basic_lib.photo.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.waitou.basic_lib.R
import com.waitou.basic_lib.photo.view.CheckView
import com.waitou.photopicker.bean.Media
import com.waitou.photopicker.config.WisdomConfig
import com.waitou.photopicker.utils.getScreenImageResize
import com.waitou.photopicker.utils.isSingleImage
import kotlinx.android.synthetic.main.bs_item_camera.view.*
import kotlinx.android.synthetic.main.bs_item_media.view.*

/**
 * auth aboom
 * date 2019-05-28
 */
class MediasAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CAPTURE: Int = 0x01
        private const val VIEW_TYPE_MEDIA: Int = 0x02
    }

    private val medias = mutableListOf<Media>()
    val selectMedias = mutableListOf<Media>()

    var checkedListener: OnCheckedChangedListener? = null
    var cameraClick: View.OnClickListener? = null
    var mediaClick: View.OnClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_CAPTURE) {
            val cameraViewHolder = CameraViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.bs_item_camera, p0, false))
            cameraViewHolder.itemView.setOnClickListener { cameraClick?.onClick(it) }
            return cameraViewHolder
        }
        val mediaViewHolder = MediaViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.bs_item_media, p0, false))
        mediaViewHolder.itemView.setOnClickListener {
            it.tag = medias[mediaViewHolder.adapterPosition]
            mediaClick?.onClick(it)
        }
        mediaViewHolder.itemView.checkView.setOnCheckedChangeListener { checkView, isChecked ->
            mediaCheckedChange(checkView, medias[mediaViewHolder.adapterPosition], mediaViewHolder.adapterPosition)
        }
        return mediaViewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return if (medias[position].isCapture()) VIEW_TYPE_CAPTURE else VIEW_TYPE_MEDIA
    }

    override fun getItemCount(): Int {
        return medias.size
    }

    override fun onBindViewHolder(holde: RecyclerView.ViewHolder, position: Int) {
        val media = medias[position]
        if (holde is CameraViewHolder) {
            holde.itemView.text.text = media.mediaName
        } else {
            WisdomConfig.getInstance().iImageEngine?.displayThumbnail(holde.itemView.media, media.uri, getScreenImageResize())
            holde.itemView.checkView.setCheckedNum(selectMediaIndexOf(media))
            holde.itemView.media.setColorFilter(if (holde.itemView.checkView.isChecked)
                Color.argb(80, 0, 0, 0) else Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP)
            holde.itemView.checkView.visibility = if (isSingleImage()) View.GONE else View.VISIBLE
            holde.itemView.duration.text = DateUtils.formatElapsedTime(media.duration / 1000)
            holde.itemView.size.text = Formatter.formatFileSize(holde.itemView.context, media.size)
        }
    }

    private fun mediaCheckedChange(checkView: CheckView, media: Media, position: Int) {
        val checkedNumIndex = selectMediaIndexOf(media)
        if (checkedNumIndex > 0) {
            selectMedias.remove(media)
        } else {
            if (selectMedias.size >= WisdomConfig.getInstance().maxSelectLimit) {
                return
            }
            selectMedias.add(media)
        }
        notifyDataSetChanged()
        checkedListener?.onChange()
    }

    interface OnCheckedChangedListener {
        fun onChange()
    }

    private fun selectMediaIndexOf(media: Media): Int {
        val indexOf = selectMedias.indexOf(media)
        return if (indexOf >= 0) indexOf + 1 else indexOf
    }

    fun replaceData(medias: List<Media>) {
        this.medias.clear()
        this.medias.addAll(medias)
        notifyDataSetChanged()
    }

    private class CameraViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

    private class MediaViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
}
