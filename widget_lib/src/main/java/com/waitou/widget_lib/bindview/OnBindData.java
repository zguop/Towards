package com.waitou.widget_lib.bindview;

/**
 * auth aboom by 2018/7/26.
 */

public interface OnBindData<T> {

    int onLayout();

    //更新数据
    void onBind(int pos, int itemCount, T t, ViewHolder holder);

    //动态设置view大小 在add到父控件时调用
    void onViewSize(int pos, int itemCount, ViewHolder holder);
}
