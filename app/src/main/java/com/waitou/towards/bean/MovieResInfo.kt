package com.waitou.towards.bean

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by waitou on 17/5/25.
 */
class MovieResInfo {

    @SerializedName("list")
    @Expose
    var list: List<MovieType>? = null

}