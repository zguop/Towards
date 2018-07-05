package com.waitou.towards.model.gallery;

import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.towards.net.DataLoader;
import com.waitou.towards.net.SimpleErrorVerify;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.recycler.XRecyclerView;

/**
 * Created by waitou on 17/2/23.
 * 妹子相册presenter
 */

public class GalleryPresenter extends XPresent<GalleryActivity> implements XRecyclerView.OnRefreshAndLoadMoreListener {

    void loadData(int page) {
        pend(DataLoader.getGankApi().getGirlPics(page)
                .compose(RxTransformerHelper.applySchedulersAndAllFilter(getV(), new SimpleErrorVerify() {
                    @Override
                    public void netError(Throwable throwable) {
                        super.netError(throwable);
                        getV().onError(page == 1);
                    }
                }))
                .map(listGankIoDayInfo -> listGankIoDayInfo.results)
                .subscribe(galleryInfo -> getV().onSuccess(galleryInfo)));
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onLoadMore(int page) {
        loadData(page);
    }

    public void finish() {
        getV().finish();
    }
}
