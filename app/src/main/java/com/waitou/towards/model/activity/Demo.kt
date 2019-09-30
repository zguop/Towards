package com.waitou.towards.model.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.waitou.towards.model.activity.coroutine.startCoroutine
import com.waitou.towards.model.activity.coroutine.startLogoPic

/**
 * auth aboom
 * date 2019/4/22
 */
class Demo : AppCompatActivity() {

    private val url3 = "http://pic1.win4000.com/wallpaper/2018-07-17/5b4ddcdb0f988.jpg"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.textSize = 16f
        textView.setTextColor(Color.BLUE)
        textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        textView.gravity = Gravity.CENTER
        textView.text = "1111111"
        textView.postDelayed({ textView!!.text = "22222222222" }, 2000)
        textView.setOnClickListener { ToastUtils.showShort(textView!!.text.toString()) }
        val content = LinearLayout(this)
        content.orientation = LinearLayout.VERTICAL
        content.addView(textView)


        val img = ImageView(this)
        content.addView(img, ViewGroup.LayoutParams(200, 200))

        textView.setOnClickListener {
            Log.e("aa" ,"协程开始之前")
            startCoroutine {
                Log.e("aa" ,"协程开始")
                val startLogoPic = startLogoPic(url3)
                Log.e("aa" ,"拿到图片 " + startLogoPic.size);

            }
            Log.e("aa" ,"协程之后")

        }

        setContentView(content, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

    }

    override fun onPostResume() {
        super.onPostResume()
    }

}
