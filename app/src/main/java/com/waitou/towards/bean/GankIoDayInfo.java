package com.waitou.towards.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by waitou on 17/2/16.
 */

public class GankIoDayInfo<T> implements Serializable{

    @SerializedName("error")
    @Expose
    public boolean error;

    @SerializedName("category")
    @Expose
    public List<String> category;

    @SerializedName("results")
    @Expose
    public T results;

}
