package com.waitou.wt_library.kit;

import android.content.res.ColorStateList;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.support.annotation.AttrRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CompoundButtonCompat;
import android.widget.CompoundButton;

import com.to.aboomy.banner.IQyIndicator;
import com.to.aboomy.banner.QyBanner;
import com.to.aboomy.banner.ScalePageTransformer;
import com.to.aboomy.theme_lib.config.ThemeUtils;
import com.to.aboomy.utils_lib.USize;


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

    @BindingAdapter({"bannerAdapter", "bannerIndicator"})
    public static void setBanner(QyBanner qyBanner, PagerAdapter pagerAdapter, IQyIndicator iQyIndicator) {
        qyBanner.setPageMargins(USize.dip2pxInt(30), USize.dip2pxInt(10), USize.dip2pxInt(30), USize.dip2pxInt(20), USize.dip2pxInt(10));
        qyBanner.setPageTransformer(true, new ScalePageTransformer(0.8f));
        qyBanner.setIndicator(iQyIndicator);
        qyBanner.setAdapter(pagerAdapter);
    }

    @BindingAdapter("buttonTintList")
    public static void setButtonTintList(CompoundButton compoundButton, @AttrRes int attr) {
        int themeAttr = ThemeUtils.getThemeAttrId(compoundButton.getContext(), attr);
        ColorStateList colorStateList = ThemeUtils.getColorStateList(compoundButton.getContext(), themeAttr);
        CompoundButtonCompat.setButtonTintList(compoundButton, colorStateList);
    }
}
