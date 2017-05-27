package com.waitou.towards.bean

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by waitou on 17/5/26.
 */
class MovieType {

    @SerializedName("showStyle")
    @Expose
    var showStyle: String? = null

    @SerializedName("videoList")
    @Expose
    var videoList: String? = null

    @SerializedName("changeOpenFlag")
    @Expose
    var changeOpenFlag: String? = null

    @SerializedName("line")
    @Expose
    var line: Int? = null

    @SerializedName("showType")
    @Expose
    var showType: String? = null

    @SerializedName("childList")
    @Expose
    var childList: MutableList<MovieInfo>? = null

    @SerializedName("moreURL")
    @Expose
    var moreURL: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("bigPicShowFlag")
    @Expose
    var bigPicShowFlag: String? = null

}