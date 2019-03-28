package com.waitou.towards.model.activity;

import com.to.aboomy.recycler_lib.IQyPresenter;
import com.to.aboomy.utils_lib.AlertToast;
import com.to.aboomy.utils_lib.UString;

/**
 * auth aboom
 * date 2018/7/22
 */
public class RecommendHelper implements IQyPresenter {

    RequestBean mRequestBean = new RequestBean();


    public void submit() {

        if (UString.isEmpty(mRequestBean.phone)) {
            AlertToast.show("手机号不能为空");
            return;
        }

        if (UString.isEmpty(mRequestBean.password)) {
            AlertToast.show("密码不能为空");
            return;
        }
        AlertToast.show("通过");

    }


}
