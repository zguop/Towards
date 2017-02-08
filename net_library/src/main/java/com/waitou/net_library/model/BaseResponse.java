package com.waitou.net_library.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by waitou on 17/1/4.
 * 基类数据 自行定义
 */

public class BaseResponse<T> {

    /**
     * 返回码，见ResponseCode枚举
     */
    @SerializedName("reason")
    @Expose
    public String reason;

    //错误描述
    @SerializedName("error_code")
    @Expose
    public String errorCode;

    @SerializedName("result")
    @Expose
    public T result;
}
