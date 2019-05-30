package com.waitou.photopicker.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * auth aboom
 * date 2019-05-24
 */
abstract class PhotoWallActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateBoxingView()
    }

    abstract fun onCreateBoxingView(): PhotoWallFragment

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}