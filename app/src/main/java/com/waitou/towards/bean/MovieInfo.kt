package com.waitou.towards.bean

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.waitou.net_library.model.Displayable
import java.io.Serializable

/**
 * Created by waitou on 17/5/26.
 */
class MovieInfo : Displayable, Serializable {

    @SerializedName("airTime")
    @Expose
    var airTime: Long? = null

    @SerializedName("duration")
    @Expose
    var duration: String? = null

    @SerializedName("loadType")
    @Expose
    var loadType: String? = null

//    @SerializedName("score")
//    @Expose
//    var score: Int? = null

    @SerializedName("angleIcon")
    @Expose
    var angleIcon: String? = null

    @SerializedName("dataId")
    @Expose
    var dataId: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("loadURL")
    @Expose
    var loadURL: String? = null

    @SerializedName("shareURL")
    @Expose
    var shareURL: String? = null

    @SerializedName("pic")
    @Expose
    var pic: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("roomId")
    @Expose
    var roomId: String? = null
}