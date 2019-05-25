package com.waitou.photopicker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * auth aboom
 * date 2019-05-24
 */
class PhotoWallActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val atlasCollection = AtlasCollection()

        atlasCollection.onCreate(this)


    }


}