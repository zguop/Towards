package com.waitou.net_library.helper;

import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.waitou.net_library.R;

/**
 * Created by waitou on 17/2/17.
 */

public class SimpleErrorVerify implements ErrorVerify {

    @Override
    public void call(Throwable throwable) {
        if (TextUtils.isEmpty(throwable.getMessage())) {
            ToastUtils.showShort(R.string.warn_net_error);
        } else {
            ToastUtils.showShort(throwable.getMessage());
        }
    }
}
