package com.waitou.towards.model.gallery;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.net_library.helper.SimpleErrorVerify;
import com.waitou.net_library.model.APIResult;
import com.waitou.towards.bean.GankResultsTypeInfo;
import com.waitou.towards.net.DataLoader;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * auth aboom
 * date 2019/4/23
 */
public class GalleryViewModule extends ViewModel {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private MutableLiveData<APIResult<List<GankResultsTypeInfo>>> liveData = new MutableLiveData<>();

    public MutableLiveData<APIResult<List<GankResultsTypeInfo>>> getLiveData() {
        return liveData;
    }

    public void getGirlPics() {
        getGirlPics(1);
    }

    public void getGirlPics(int page) {
        Disposable subscribe = DataLoader.getGankApi().getGirlPics(page)
                .compose(RxTransformerHelper.applySchedulersAndAllFilter(new SimpleErrorVerify() {
                    @Override
                    public void call(Throwable throwable) {
                        liveData.setValue(APIResult.failure(page, throwable.getMessage()));
                    }
                }))
                .map(o -> o.results)
                .subscribe(galleryInfo -> liveData.setValue(APIResult.success(galleryInfo)));
        mCompositeDisposable.add(subscribe);
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.clear();
    }
}
