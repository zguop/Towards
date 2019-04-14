package com.waitou.towards.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * auth aboom
 * date 2019/4/13
 */
public class FunctionInfo {

    @SerializedName("picUrl")
    @Expose
    public String picUrl;

    @SerializedName("typeName")
    @Expose
    public String typeName;

    @SerializedName("type")
    @Expose
    public int type;

    @SerializedName("link")
    @Expose
    public String link;

    @SerializedName("value")
    @Expose
    public String value;
}
