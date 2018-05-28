package com.waitou.wt_library.kit;

import android.content.res.ColorStateList;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.support.annotation.AttrRes;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CompoundButtonCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.to.aboomy.theme_lib.config.ThemeUtils;
import com.waitou.wt_library.rx.RxBus;


/**
 * Created by waitou on 17/1/11.
 */
@InverseBindingMethods({
        @InverseBindingMethod(type = ViewPager.class, attribute = "currentItem"),
})
public class UtilsBinding {

    @BindingAdapter(value = {"currentItemAttrChanged"}, requireAll = false)
    public static void setPageCurrent(ViewPager viewPager, final InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener != null) {
            viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    inverseBindingListener.onChange();
                }
            });
        }
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

    @BindingAdapter("buttonTintList")
    public static void setButtonTintList(CompoundButton compoundButton, @AttrRes int attr) {
        int themeAttr = ThemeUtils.getThemeAttrId(compoundButton.getContext(), attr);
        ColorStateList colorStateList = ThemeUtils.getColorStateList(compoundButton.getContext(), themeAttr);
        CompoundButtonCompat.setButtonTintList(compoundButton, colorStateList);
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
