package com.waitou.basic_lib.photo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.waitou.basic_lib.R
import com.waitou.imgloader_lib.ImageLoader
import com.waitou.photopicker.bean.Media
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

    private val medias: MutableList<Media> = mutableListOf()
    var cameraClick: View.OnClickListener? = null


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_CAPTURE) {
            return CameraViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.bs_item_camera, p0, false))
        }
        return MediaViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.bs_item_media, p0, false))
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
            holde.itemView.setOnClickListener(cameraClick)
        } else {
            ImageLoader.displayImage(holde.itemView.media, media.uri)
        }
    }

    fun replaceData(medias: List<Media>) {
        this.medias.clear()
        this.medias.addAll(medias)
        notifyDataSetChanged()
    }

    private class CameraViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

    private class MediaViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
}
