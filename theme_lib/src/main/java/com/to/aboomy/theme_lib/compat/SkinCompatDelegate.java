package com.to.aboomy.theme_lib.compat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.to.aboomy.theme_lib.skin.SkinAttr;
import com.to.aboomy.theme_lib.skin.SkinAttrType;
import com.to.aboomy.theme_lib.skin.SkinView;
import com.to.aboomy.theme_lib.utils.ThemeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * auth aboom
 * date 2019-08-22
 */
public class SkinCompatDelegate implements LayoutInflater.Factory2 {

    private final SkinCompatViewInflater skinCompatViewInflater;
    private List<SkinView> skinViews = new ArrayList<>();

    SkinCompatDelegate() {
        skinCompatViewInflater = new SkinCompatViewInflater();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        if (context instanceof AppCompatActivity) {
            view = ((AppCompatActivity) context).getDelegate().createView(parent, name, context, attrs);
        }
        if (view == null) {
            view = skinCompatViewInflater.createViewFromTag(context, name, attrs);
        }
        if (view != null) {
            List<SkinAttr> skinAttrsList = getSkinAttrs(attrs, context);
            if (skinAttrsList.size() > 0) {
                skinViews.add(new SkinView(view, skinAttrsList));
            } else if (view instanceof SkinCompatSupportable) {
                skinViews.add(new SkinView(view, null));
            }
        }
        return view;
    }

    void applySkin() {
        for (SkinView skinView : skinViews) {
            if (skinView != null) {
                skinView.apply();
            }
        }
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }

    @NonNull
    private List<SkinAttr> getSkinAttrs(AttributeSet attrs, Context context) {
        List<SkinAttr> skinAttrsList = new ArrayList<>();
        SkinAttr skinAttr;
        //遍历所有属性
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //获取到当前的属性名字，
            String attributeName = attrs.getAttributeName(i);
            //改方法获取到枚举中定义好的需要更改的属性进行匹配
            SkinAttrType attrType = getSupportAttrType(attributeName);
            if (attrType == null) {
                continue;
            }
            //获取当前属性对应的值 并解析，如果是使用?attr/ 或者是 @color/属性
            String attributeValue = attrs.getAttributeValue(i);
            if (attributeValue.startsWith("?") || attributeValue.startsWith("@")) {
                //获取到该资源的id
                int id = ThemeUtils.getAttrResId(attributeValue);
                if (id != 0) {
                    //通过资源id 获取到资源的名称
                    String entryName = context.getResources().getResourceEntryName(id);
                    //如果匹配 资源名称 表示都是使用了换肤的 属性则保存起来
                    if (entryName.equals(ThemeUtils.COLOR_PRIMARY) || entryName.startsWith(ThemeUtils.ATTR_PREFIX)) {
                        String typeName = context.getResources().getResourceTypeName(id);
                        skinAttr = new SkinAttr(attrType, entryName, attributeName, typeName);
                        skinAttrsList.add(skinAttr);
                    }
                }
            }
        }
        return skinAttrsList;
    }

    private SkinAttrType getSupportAttrType(String attrName) {
        for (SkinAttrType attrType : SkinAttrType.values()) {
            if (attrType.getAttrType().equals(attrName))
                return attrType;
        }
        return null;
    }
}
