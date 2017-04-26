package com.waitou.towards.net;

import com.waitou.net_library.helper.ErrorVerify;
import com.waitou.towards.R;
import com.waitou.wt_library.kit.AlertToast;

/**
 * Created by waitou on 17/1/11.
 */

public class SimpleErrorVerify implements ErrorVerify {
    @Override
    public void call(String code, String desc) {
        AlertToast.show(desc);
    }

    @Override
    public void netError(Throwable throwable) {
        AlertToast.show(R.string.warn_net_error);
    }
}
