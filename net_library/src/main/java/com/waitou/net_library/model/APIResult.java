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
     * 分页加载需要用的pageIndex
     */
    @Expose
    private int pageIndex;

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
        return success(-1, data);
    }

    public static <T> APIResult<T> success(int pageIndex, T data) {
        APIResult<T> apiResult = create();
        apiResult.setSuccess(true);
        apiResult.setPageIndex(pageIndex);
        apiResult.setData(data);
        return apiResult;
    }

    public static <T> APIResult<T> failure(String message) {
        return failure(-1, message);
    }

    public static <T> APIResult<T> failure(int pageIndex) {
        APIResult<T> apiResult = create();
        apiResult.setSuccess(false);
        apiResult.setPageIndex(pageIndex);
        apiResult.setMessage(Utils.getApp().getString(R.string.warn_net_error));
        return apiResult;
    }

    public static <T> APIResult<T> failure(int pageIndex, String message) {
        APIResult<T> apiResult = create();
        apiResult.setSuccess(false);
        apiResult.setPageIndex(pageIndex);
        apiResult.setMessage(message);
        return apiResult;
    }


    public static <T> APIResult<T> create() {
        return new APIResult<>();
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailed() {
        return !success;
    }

    public boolean isFirstPage() {
        return pageIndex == 1;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
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
