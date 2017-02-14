package com.waitou.towards.model.jokes.fragment.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.waitou.towards.util.AlertToast;
import com.waitou.wt_library.imageloader.ILFactory;
import com.waitou.wt_library.view.viewpager.WTPagerAdapter;

import java.util.List;



/**
 * Created by waitou on 17/2/12.
 */

public class BannerAdapter extends WTPagerAdapter<String> {

    public BannerAdapter(List<String> data) {
        super(data);
    }

    @Override
    public View newView(Context context, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ILFactory.getLoader().loadNet(imageView, mData.get(position), null);
        imageView.setOnClickListener(v -> AlertToast.show("点击了 " + position));
        return imageView;
    }
}
