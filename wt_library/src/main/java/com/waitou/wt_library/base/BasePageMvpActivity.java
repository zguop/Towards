package com.waitou.wt_library.base;

/**
 * auth aboom
 * date 2019/4/7
 */
public abstract class BasePageMvpActivity<P extends IPresent> extends BasePageActivity {

    private P p;

    @SuppressWarnings("unchecked")
    protected P getP() {
        if (p == null) {
            p = newP();
        }
        if (p != null) {
            if (!p.hasV()) {
                p.attachV(this);
            }
        }
        return p;
    }

    protected abstract P newP();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (p != null) {
            p.detachV();
        }
        p = null;
    }
}
