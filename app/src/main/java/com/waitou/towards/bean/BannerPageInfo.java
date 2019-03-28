package com.waitou.towards.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by waitou on 17/2/16.
 */

public class BannerPageInfo {

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("banner")
    @Expose
    public String banner;

    @SerializedName("keyword")
    @Expose
    public String keyword;

    @SerializedName("description")
    @Expose
    public String description;

    public int linkType;

    @SerializedName("link")
    @Expose
    public String link;
}
