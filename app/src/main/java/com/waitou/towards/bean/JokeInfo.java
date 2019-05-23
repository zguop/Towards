package com.waitou.towards.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.to.aboomy.recycler_lib.Displayable;

/**
 * Created by waitou on 17/1/4.
 */

public class JokeInfo implements Displayable {

    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("hashId")
    @Expose
    public String hashId;

    @SerializedName("unixtime")
    @Expose
    public long unixtime;

    @SerializedName("url")
    @Expose
    public String url;
}
