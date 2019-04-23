package com.waitou.net_library.model;

import com.blankj.utilcode.util.Utils;
import com.google.gson.annotations.Expose;
import com.waitou.net_library.R;

import java.io.Serializable;

/**
 * auth aboom
 * date 2019/4/23
 * <p>
 * Expose gson对象序列化，包含的字段
 * SerializedName 定义属性序列化后的名称
 */
public class APIResult<T> implements Serializable {
    /**
     * 是否成功
     */
    @Expose
    private boolean success;

    /**
     * 返回结果码
     */
    @Expose
    private int code;

    /**
     * 返回结果描述
     */
    @Expose
    private String message;

    /**
     * 数据
     */
    @Expose
    private T data;


    public static <T> APIResult<T> success(T data) {
        APIResult<T> apiResult = create();
        apiResult.setSuccess(true);
        apiResult.setData(data);
        return apiResult;
    }

    public static <T> APIResult<T> failure(String message) {
        return failure(0, message);
    }

    public static <T> APIResult<T> failure(int code) {
        APIResult<T> apiResult = create();
        apiResult.setSuccess(false);
        apiResult.setCode(code);
        apiResult.setMessage(Utils.getApp().getString(R.string.warn_net_error));
        return apiResult;
    }

    public static <T> APIResult<T> failure(int code, String message) {
        APIResult<T> apiResult = create();
        apiResult.setSuccess(false);
        apiResult.setCode(code);
        apiResult.setMessage(message);
        return apiResult;
    }


    public static <T> APIResult<T> create() {
        return new APIResult<>();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
