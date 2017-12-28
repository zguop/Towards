package com.waitou.towards.bean

import java.io.Serializable

/**
 * Created by waitou on 17/6/30.
 */
data class MovieProjectInfo(var title: String,
                            var pic: String,
                            var dataId: String,
                            var airTime: String,
                            var moreUrl: String,
                            var loadType: String) : Serializable