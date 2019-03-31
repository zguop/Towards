package com.waitou.towards.model.activity;

import android.text.TextUtils;

import com.to.aboomy.recycler_lib.IPresenter;

/**
 * auth aboom
 * date 2018/7/22
 */
public class RecommendHelper implements IPresenter {

    RequestBean mRequestBean = new RequestBean();



    public void submit() {

        if (TextUtils.isEmpty(mRequestBean.phone)) {
            return;
        }

        if (TextUtils.isEmpty(mRequestBean.password)) {
            return;
        }

    }





}
