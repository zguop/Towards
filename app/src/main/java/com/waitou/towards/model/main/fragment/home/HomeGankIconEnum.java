package com.waitou.towards.model.main.fragment.home;

import com.waitou.towards.R;

/**
 * auth aboom
 * date 2019-04-27
 */
public enum HomeGankIconEnum {
    all("all", R.drawable.svg_ic_all),
    ios("iOS", R.drawable.svg_ic_ios),
    app("App", R.drawable.svg_ic_app),
    web("前端", R.drawable.svg_ic_qian),
    movie("休息视频", R.drawable.svg_ic_movie),
    source("拓展资源", R.drawable.svg_ic_tuozhan);

    private String desc;
    private int    resId;

    HomeGankIconEnum(String desc, int resId) {
        this.desc = desc;
        this.resId = resId;
    }

    public String getDesc() {
        return desc;
    }

    public int getResId() {
        return resId;
    }
}