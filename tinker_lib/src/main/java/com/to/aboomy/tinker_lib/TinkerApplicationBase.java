package com.to.aboomy.tinker_lib;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * auth aboom
 * date 2018/4/16
 * tinkerFlags, tinker支持的类型，dex,library，还是全部都支持！
 */
public class TinkerApplicationBase extends TinkerApplication {

    protected TinkerApplicationBase(String delegateClassName) {
        super(ShareConstants.TINKER_ENABLE_ALL, delegateClassName);
    }

    protected TinkerApplicationBase(int tinkerFlags, String delegateClassName) {
        super(ShareConstants.TINKER_ENABLE_ALL, delegateClassName);
    }

    protected TinkerApplicationBase(int tinkerFlags, String delegateClassName, String loaderClassName, boolean tinkerLoadVerifyFlag) {
        super(ShareConstants.TINKER_ENABLE_ALL, delegateClassName, loaderClassName, false);
    }
}
