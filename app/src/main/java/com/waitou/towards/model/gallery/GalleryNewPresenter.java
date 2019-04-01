package com.waitou.towards.model.gallery;

import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.towards.net.DataLoader;
import com.waitou.towards.net.SimpleErrorVerify;
import com.waitou.wt_library.base.XPresent;

/**
 * auth aboom
 * date 2019/3/31
 */
public class GalleryNewPresenter extends XPresent<GalleryNewActivity> {

    void loadData(int page) {
        pend(DataLoader.getGankApi().getGirlPics(page)
                .compose(RxTransformerHelper.applySchedulersAndAllFilter(new SimpleErrorVerify() {
                    @Override
                    public void netError(Throwable throwable) {
                        super.netError(throwable);
                        getV().onError(page == 1);
                    }
                }))
                .map(o -> o.results)
                .subscribe(galleryInfo -> getV().onSuccess(galleryInfo)));
    }
}
