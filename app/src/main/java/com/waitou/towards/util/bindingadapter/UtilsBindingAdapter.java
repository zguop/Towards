package com.waitou.towards.util.bindingadapter;

import android.databinding.BindingAdapter;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.waitou.towards.R;
import com.waitou.wt_library.rx.RxBus;


/**
 * Created by waitou on 17/1/11.
 */

public class UtilsBindingAdapter {

    /**
     * 设置viewpager的adapter
     */
    @BindingAdapter("adapter")
    public static void setAdapter(ViewPager pager, PagerAdapter adapter) {
        pager.setAdapter(adapter);
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

    @BindingAdapter("home_title")
    public static void setImageDrawable(ImageView view, int position) {
        switch (position) {
            case 0:
                view.setImageDrawable(ActivityCompat.getDrawable(view.getContext(), R.drawable.icon__recommended));
                break;
            case 1:
                view.setImageDrawable(ActivityCompat.getDrawable(view.getContext(), R.drawable.icon_cargo));
                break;
            case 2:
                view.setImageDrawable(ActivityCompat.getDrawable(view.getContext(), R.drawable.icon_android));
                break;
        }
    }
}
