package com.waitou.towards.net;

import com.waitou.net_library.helper.ErrorVerify;

/**
 * Created by waitou on 17/2/17.
 */

public class EmptyErrorVerify implements ErrorVerify {
    @Override
    public void call(String code, String desc) {

    }

    @Override
    public void netError(Throwable throwable) {

    }
}
