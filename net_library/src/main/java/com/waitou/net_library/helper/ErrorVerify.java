package com.waitou.net_library.helper;

/**
 * Created by waitou on 17/1/4.
 */

public interface ErrorVerify {
    void call(String code, String desc);

    void netError(Throwable throwable);
}
