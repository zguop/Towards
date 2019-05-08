package com.waitou.towards.model.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.view.View;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.waitou.imgloader_lib.HsRoundImageView;
import com.waitou.imgloader_lib.ImageLoader;
import com.waitou.towards.R;
import com.waitou.wt_library.base.BasePageActivity;

/**
 * auth aboom
 * date 2019-05-07
 */
public class ColorActivity extends BasePageActivity {

    private String url = "http://hbimg.b0.upaiyun.com/49c9355cf670b5f01a7dd750c40f6a2ee8fbf60d1bbb5-kdsjmn_fw658";
    private String url1 = "http://img0.pconline.com.cn/pconline/1506/29/6638168_1754_thumb.jpg";
    private String url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557327833941&di=4be9015403fd966f1a32711ade360ea4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201710%2F01%2F20171001105830_wWQej.jpeg";
    private String url3 = "http://pic1.win4000.com/wallpaper/2018-07-17/5b4ddcdb0f988.jpg";

    @Override
    public void reloadData() {
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        showContent();
//        CustomCircleImageView viewById = findViewById(R.id.img);
//        ShadowLayout shadowLayout = findViewById(R.id.shadow);
//
//        ImageLoader.displayImage(viewById, url1,new BitmapImageViewTarget(viewById) {
//            @Override
//            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
////                super.onResourceReady(resource, transition);
//                viewById.setImageBitmap(resource);
//                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
//                    @Override
//                    public void onGenerated(Palette palette) {
//                        int darkMutedColor = palette.getDarkMutedColor(Color.BLUE);
//                        int darkVibrantColor = palette.getDarkVibrantColor(Color.BLUE);
//                        int lightVibrantColor = palette.getLightVibrantColor(Color.BLUE);
//                        int lightMutedColor = palette.getLightMutedColor(Color.BLUE);
//                        int vibrantColor = palette.getVibrantColor(Color.BLUE);
//                        int mutedColor = palette.getMutedColor(Color.BLUE);
//
////                        int rgb = palette.getDominantSwatch().getRgb();
//                        Log.e("aa" ,"onResourceReady " + darkMutedColor);
////                        shadowLayout.invalidateShadow(vibrantColor);
//
//
//                        findViewById(R.id.darkMutedColor).setBackgroundColor(darkMutedColor);
//                        findViewById(R.id.darkVibrantColor).setBackgroundColor(darkVibrantColor);
//                        findViewById(R.id.lightMutedColor).setBackgroundColor(lightMutedColor);
//                        findViewById(R.id.lightVibrantColor).setBackgroundColor(lightVibrantColor);
//                        findViewById(R.id.mutedColor).setBackgroundColor(mutedColor);
//                        findViewById(R.id.vibrantColor).setBackgroundColor(vibrantColor);
//
//                    }
//                });
//
//            }
//        });



        HsRoundImageView hsRoundImageView =findViewById(R.id.img1);
        ImageLoader.displayImage(hsRoundImageView,url3,new BitmapImageViewTarget(hsRoundImageView){
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                hsRoundImageView.setImageBitmap(resource);

                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(@Nullable Palette palette) {
                        int darkMutedColor = palette.getDarkMutedColor(Color.BLUE);
                        int darkVibrantColor = palette.getDarkVibrantColor(Color.BLUE);
                        int lightVibrantColor = palette.getLightVibrantColor(Color.BLUE);
                        int lightMutedColor = palette.getLightMutedColor(Color.BLUE);
                        int vibrantColor = palette.getVibrantColor(Color.BLUE);
                        int mutedColor = palette.getMutedColor(Color.BLUE);

                        findViewById(R.id.darkMutedColor).setBackgroundColor(darkMutedColor);
                        findViewById(R.id.darkVibrantColor).setBackgroundColor(darkVibrantColor);
                        findViewById(R.id.lightMutedColor).setBackgroundColor(lightMutedColor);
                        findViewById(R.id.lightVibrantColor).setBackgroundColor(lightVibrantColor);
                        findViewById(R.id.mutedColor).setBackgroundColor(mutedColor);
                        findViewById(R.id.vibrantColor).setBackgroundColor(vibrantColor);
                    }
                });
            }
        });

    }

    @Override
    public View getContentView() {
        return ff(R.layout.activity_color);
    }
}
