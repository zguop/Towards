package com.waitou.towards.model.main;

import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;

import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.net_library.model.RequestParams;
import com.waitou.towards.ExtraValue;
import com.waitou.towards.bean.BannerPageInfo;
import com.waitou.towards.model.main.fragment.CircleFragment;
import com.waitou.towards.model.main.fragment.home.HomeCommendFragment;
import com.waitou.towards.model.main.fragment.joke.JokeContentFragment;
import com.waitou.towards.net.DataLoader;
import com.waitou.towards.net.SimpleErrorVerify;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.kit.Kits;
import com.waitou.wt_library.view.viewpager.SingleViewPagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by waitou on 17/1/27.
 */

public class MainPresenter extends XPresent<MainActivity> implements MainContract.MainPresenter, SingleViewPagerAdapter.Presenter ,Serializable{

    private List<Fragment> homeFragments = new ArrayList<>();
    private List<Fragment> jokeFragments = new ArrayList<>();

    private MainContract.TextJokeView mTextJokeView;
    private MainContract.HomeView     mHomeView;

    public MainPresenter(MainContract.HomeView homeView, MainContract.TextJokeView textJokeView) {
        this.mTextJokeView = textJokeView;
        this.mHomeView = homeView;
        mTextJokeView.setPresenter(this);
        mHomeView.setPresenter(this);
    }

    @Override
    public List<Fragment> getHomeFragmentList() {
        if (homeFragments.size() > 0) {
            return homeFragments;
        }
        homeFragments.add(HomeCommendFragment.getInstance(this));
        homeFragments.add(new CircleFragment());
        homeFragments.add(new CircleFragment());
        return homeFragments;
    }

    @Override
    public List<Fragment> getJokeFragmentList() {
        if (jokeFragments.size() > 0) {
            return jokeFragments;
        }
        jokeFragments.add(JokeContentFragment.getInstance(0, this));
        jokeFragments.add(JokeContentFragment.getInstance(1, this));
        return jokeFragments;
    }

    /**
     * 笑话数据加载
     */
    @Override
    public void loadJokeData(int page, int type) {
        RequestParams params = new RequestParams();
        params.put("key", ExtraValue.KEY);
        params.put("page", page);
        switch (type) {
            case 0:
                params.put("type", "");
                break;
            case 1:
                params.put("type", "pic");
                break;
            case 2:

                break;
        }

        getV().pend(DataLoader.getJokeApi().getTextJoke(params)
                .compose(RxTransformerHelper.applySchedulersResult(getV(), new SimpleErrorVerify() {
                    @Override
                    public void call(String code, String desc) {
                        super.call(code, desc);
                        mTextJokeView.getCurrentJokeFragment().showError(page == 1);
                    }

                    @Override
                    public void netError(Throwable throwable) {
                        super.netError(throwable);
                        mTextJokeView.getCurrentJokeFragment().showError(page == 1);
                    }
                }))
                .subscribe(jokeInfo -> {
                    mTextJokeView.getCurrentJokeFragment().success(page, jokeInfo);
                }));
    }

    @Override
    public void start() {
    }

    /**
     * 广告位的点击方法
     */
    public void onBannerItemClick(BannerPageInfo url, int position) {

    }

    /**
     * 加载HomeCommendFragment首页数据
     */
    public void loadHomeData() {
        getV().pend(Observable.zip(DataLoader.getGithubApi().getBannerPage(), DataLoader.getGithubApi().getHomeData(), Pair::create)
                .compose(RxTransformerHelper.applySchedulers())
                .map(pair -> {
                    if (pair.first != null && pair.first.result != null && pair.first.result.size() > 0) {
                        int dayOfWeek = Kits.Date.getDayOfWeek(new Date()) - 1;
                        ((HomeCommendFragment) mHomeView.getCurrentHomeFragment()).onBannerSuccess(pair.first.result.get(dayOfWeek));
                    }
                    if (pair.second != null && pair.second.result != null && pair.second.result.function.size() > 0) {
                        ((HomeCommendFragment) mHomeView.getCurrentHomeFragment()).onFunctionSuccess(pair.second.result.function);
                    }
                    return Kits.Date.getCurrentDate().split("-");
                })
                .observeOn(Schedulers.io())
                .flatMap((currentDate -> DataLoader.getGankApi().getGankIoDay(currentDate[0], currentDate[1], currentDate[2])))
                .observeOn(AndroidSchedulers.mainThread())
                .filter(RxTransformerHelper.verifyNotEmpty())
                .subscribe(gankIoDayInfo -> {
                    ((HomeCommendFragment) mHomeView.getCurrentHomeFragment()).onSuccess(gankIoDayInfo);
                }, throwable -> {
                    ((HomeCommendFragment) mHomeView.getCurrentHomeFragment()).onError(throwable);
                }));
    }
}
