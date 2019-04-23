package com.waitou.net_library.helper;

import com.blankj.utilcode.util.ToastUtils;
import com.waitou.net_library.R;

/**
 * Created by waitou on 17/2/17.
 */

public interface SimpleErrorVerify extends ErrorVerify {
    @Override
    default void call(Throwable throwable) {
        ToastUtils.showShort(R.string.warn_net_error);
    }
}
