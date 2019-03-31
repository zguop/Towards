package com.waitou.towards.net;

import com.blankj.utilcode.util.ToastUtils;
import com.waitou.net_library.helper.ErrorVerify;
import com.waitou.towards.R;

/**
 * Created by waitou on 17/1/11.
 */

public class SimpleErrorVerify implements ErrorVerify {
    @Override
    public void call(String code, String desc) {
        ToastUtils.showShort(desc);
    }

    @Override
    public void netError(Throwable throwable) {
        ToastUtils.showShort(R.string.warn_net_error);
    }
}
