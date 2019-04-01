package com.waitou.towards.model.gallery.helper;

import android.view.View;

import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;


public class ScaleTransformer implements GalleryLayoutManager.ItemTransformer {

    @Override
    public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {
        item.setPivotX(item.getWidth() / 2f);
        item.setPivotY(item.getHeight() / 2f);
        float scale = 1 - 0.2f * Math.abs(fraction);
        item.setScaleX(scale);
        item.setScaleY(scale);
    }
}
