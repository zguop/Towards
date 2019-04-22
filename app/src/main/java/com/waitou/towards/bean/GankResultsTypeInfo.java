package com.waitou.towards.bean;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.waitou.net_library.model.Displayable;

import java.util.List;

/**
 * Created by waitou on 17/2/16.
 */

public class GankResultsTypeInfo implements  Displayable, com.to.aboomy.recycler_lib.Displayable {

    @SerializedName("_id")
    @Expose
    public String _id;  //58a50780421aa9662dc74098

    @SerializedName("createdAt")
    @Expose
    public String createdAt; // 2017-02-15T17:37:50.42Z

    @SerializedName("desc")
    @Expose
    public String desc; //Android 上个性自定义 Loader

    @SerializedName("images")
    @Expose
    public List<String> images;

    @SerializedName("publishedAt")
    @Expose
    public String publishedAt; //2017-02-16T10:07:37.13Z

    @SerializedName("source")
    @Expose
    public String source; //chrome

    @SerializedName("type")
    @Expose
    public String type; //Android 

    @SerializedName("url")
    @Expose
    public String url;

    @SerializedName("used")
    @Expose
    public boolean used;

    @SerializedName("who")
    @Expose
    public String who; //dmj

    /**
     * 是否显示今日推荐
     */
    public boolean isShowTitle;

    /**
     * 这个字段在美女相册里有用
     */
    public boolean isShowImageUrl;

    /**
     * 首页右边的小图标
     */
    public Drawable typeLogo;

}
