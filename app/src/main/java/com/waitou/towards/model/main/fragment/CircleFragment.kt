package com.waitou.towards.model.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.waitou.towards.R
import com.waitou.wt_library.base.LazyFragment

/**
 * Created by waitou on 16/12/23.
 */

class CircleFragment : LazyFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
