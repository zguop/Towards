package com.waitou.net_library.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by waitou on 17/5/26.
 */

public class MovieBaseResponse<T> {

    @SerializedName("code")
    @Expose
    public int code;

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("ret")
    @Expose
    public T result;


}
