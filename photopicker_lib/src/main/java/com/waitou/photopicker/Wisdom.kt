package com.waitou.photopicker

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import com.waitou.photopicker.bean.ResultMedia
import com.waitou.photopicker.config.WisdomBuilder
import com.waitou.photopicker.config.ofAll
import java.lang.ref.WeakReference

/**
 * auth aboom
 * date 2019-05-30
 */
class Wisdom private constructor(sojourn: Any) {

    internal val sojournReference: WeakReference<Any> = WeakReference(sojourn)

    @JvmOverloads
    fun config(mineType: Int = ofAll()): WisdomBuilder {
        return WisdomBuilder(this, mineType)
    }

    companion object {

        const val EXTRA_RESULT_SELECTION = "extra_result_selection"

        @JvmStatic
        fun obtainResult(data: Intent): List<ResultMedia> {
            return data.getParcelableArrayListExtra(EXTRA_RESULT_SELECTION)
        }

        @JvmStatic
        fun of(activity: Activity): Wisdom {
            return Wisdom(activity)
        }

        @JvmStatic
        fun of(fragment: Fragment): Wisdom {
            return Wisdom(fragment)
        }
    }
}
