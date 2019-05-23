package com.waitou.towards.model.main.fragment.home;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.net_library.model.APIResult;
import com.waitou.towards.bean.HomeDataInfo;
import com.waitou.towards.net.DataLoader;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * auth aboom
 * date 2019-05-05
 */
public class HomeNewViewModule extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<APIResult<List<HomeDataInfo>>> mutableLiveData = new MutableLiveData<>();

    public void loadNewHomeData() {
        Disposable subscribe = DataLoader.getGithubApi().getHomeData()
                .compose(RxTransformerHelper.applySchedulersAndAllFilter(throwable ->
                    mutableLiveData.setValue(APIResult.failure(throwable.getMessage()))
                )).subscribe(data ->
                        mutableLiveData.setValue(APIResult.success(data))
                );
        compositeDisposable.add(subscribe);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
