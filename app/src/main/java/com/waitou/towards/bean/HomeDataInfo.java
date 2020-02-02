package com.waitou.towards.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.to.aboomy.recycler_lib.adapter.Displayable;

import java.io.Serializable;
import java.util.List;

/**
 * auth aboom
 * date 2019-05-05
 */
public class HomeDataInfo implements Displayable, Serializable {

    @SerializedName("template")
    @Expose
    public int template;

    @SerializedName("templateJson")
    @Expose
    public List<FunctionInfo> templateJson;
}
