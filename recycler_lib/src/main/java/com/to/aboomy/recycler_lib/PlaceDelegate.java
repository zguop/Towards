package com.to.aboomy.recycler_lib;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * auth aboom
 * date 2019-05-06
 */
public class  PlaceDelegate extends AdapterDelegate {

    @Override
    public boolean isForViewType(@NonNull Displayable displayable) {
        return displayable instanceof Place;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_place;
    }

    @Override
    public void convert(BaseViewHolder helper, Displayable data, int position) {
        Place place = (Place) data;
        View itemView = helper.itemView;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
        layoutParams.height = place.getHeight();
        layoutParams.leftMargin = place.getLeftMargin();
        itemView.setLayoutParams(layoutParams);
        itemView.setBackgroundColor(place.getBackgroundColor());
    }
}
