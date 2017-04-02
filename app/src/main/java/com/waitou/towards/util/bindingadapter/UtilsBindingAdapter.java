package com.waitou.towards.util.bindingadapter;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.waitou.wt_library.rx.RxBus;


/**
 * Created by waitou on 17/1/11.
 */
@InverseBindingMethods({
        @InverseBindingMethod(type = ViewPager.class, attribute = "currentItem"),
})

public class UtilsBindingAdapter {

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

    @BindingAdapter(value = {"currentItemAttrChanged"}, requireAll = false)
    public static void setPageCurrent(ViewPager viewPager, final InverseBindingListener inverseBindingListener) {
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (inverseBindingListener != null) {
                    inverseBindingListener.onChange();
                }
            }
        });
    }


    public interface PageScrolled {
        void pageScrolled(int position, float positionOffset, int positionOffsetPixels);
    }

    public interface OnPageSelected {
        void onPageSelected(int position);
    }

    public interface PageScrollStateChanged {
        void pageScrollStateChanged(int state);
    }

}
