package com.waitou.towards.util.bindingadapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.waitou.wt_library.rx.RxBus;


/**
 * Created by waitou on 17/1/11.
 */

public class UtilsBindingAdapter {

    @BindingAdapter("onClick")
    public static void onClick(View view ,View.OnClickListener clickListener){
        view.setOnClickListener(clickListener);
    }


    @BindingAdapter({"code", "post"})
    public static void post(View view, int code, Object o) {
        view.setOnClickListener(v -> RxBus.getDefault().post(code, o));
    }

    /**
     * view 点击 发送一个post
     */
    @BindingAdapter("post")
    public static void post(View view, Object o) {
        view.setOnClickListener(v -> RxBus.getDefault().post(o));
    }

    @BindingAdapter("label_color")
    public static void setLabeBackground(FloatingActionButton view, Drawable drawable) {
//        view.getla();
    }
}
