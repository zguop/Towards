package com.waitou.towards.model.presenter;

import android.support.v4.app.Fragment;

import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.net_library.model.RequestParams;
import com.waitou.towards.ExtraValue;
import com.waitou.towards.model.MainActivity;
import com.waitou.towards.model.jokes.contract.MainContract;
import com.waitou.towards.model.jokes.fragment.home.HomeCommendFragment;
import com.waitou.towards.model.jokes.fragment.joke.JokeContentFragment;
import com.waitou.towards.net.DataLoader;
import com.waitou.towards.net.SimpleErrorVerify;
import com.waitou.wt_library.base.XPresent;

import java.util.ArrayList;
import java.util.List;

import static com.waitou.towards.ExtraValue.EXTRA_VALUE_0;
import static com.waitou.towards.ExtraValue.EXTRA_VALUE_1;

/**
 * Created by waitou on 17/1/27.
 */

public class MainPresenter extends XPresent<MainActivity> implements MainContract.MainPresenter {

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
        homeFragments.add(HomeCommendFragment.getInstance(this));
        homeFragments.add(HomeCommendFragment.getInstance(this));
        return homeFragments;
    }

    @Override
    public List<Fragment> getJokeFragmentList() {
        if (jokeFragments.size() > 0) {
            return jokeFragments;
        }
        jokeFragments.add(JokeContentFragment.getInstance(EXTRA_VALUE_0, this));
        jokeFragments.add(JokeContentFragment.getInstance(EXTRA_VALUE_1, this));
        return jokeFragments;
    }

    @Override
    public void loadJokeData(int page, int type) {
        RequestParams params = new RequestParams();
        params.put("key", ExtraValue.KEY);
        params.put("page", page);
        switch (type) {
            case ExtraValue.EXTRA_VALUE_0:
                params.put("type", "");
                break;
            case ExtraValue.EXTRA_VALUE_1:
                params.put("type", "pic");
                break;
            case ExtraValue.EXTRA_VALUE_2:

                break;
        }

        getV().pend(DataLoader.getInstance().getTextJoke(params)
                .compose(RxTransformerHelper.applySchedulersResult(getV(), new SimpleErrorVerify() {
                    @Override
                    public void call(String code, String desc) {
                        super.call(code, desc);
                        mTextJokeView.getCurrentJokeFragment().showError(page == ExtraValue.EXTRA_VALUE_1);
                    }

                    @Override
                    public void netError(Throwable throwable) {
                        super.netError(throwable);
                        mTextJokeView.getCurrentJokeFragment().showError(page == ExtraValue.EXTRA_VALUE_1);
                    }
                }))
                .subscribe(jokeInfo -> {
                    mTextJokeView.getCurrentJokeFragment().success(page, jokeInfo);
                }));
    }

    @Override
    public void start() {
    }


//    @Override
//    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
//        homePos.set(position);
//        homePos.notifyChange();
//
//
//    }

//    @Override
//    public void setFired(boolean fired) {
//
//    }
//
//    public void setImgUrl(String url) {
//        imgUrl.set(url);
//        imgUrl.notifyPropertyChanged(BR.imgUrl);
//    }

}
