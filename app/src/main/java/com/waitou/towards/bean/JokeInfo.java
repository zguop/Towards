package com.waitou.towards.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.waitou.net_library.model.BaseRequest;
import com.waitou.net_library.model.Displayable;

/**
 * Created by waitou on 17/1/4.
 */

public class JokeInfo extends BaseRequest implements Displayable{

    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("hashId")
    @Expose
    public String hashId;

    @SerializedName("unixtime")
    @Expose
    public long unixtime;

    @SerializedName("updatetime")
    @Expose
    public String updatetime;

    @SerializedName("url")
    @Expose
    public String url;
}
