package com.waitou.towards.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * auth aboom
 * date 2019/4/13
 */
public class FunctionInfo implements Serializable {

    @SerializedName("picUrl")
    @Expose
    public String picUrl;

    @SerializedName("type")
    @Expose
    public int type;

    @SerializedName("link")
    @Expose
    public String link;

    @SerializedName("value")
    @Expose
    public String value;

    @SerializedName("description")
    @Expose
    public String description;
    
}
