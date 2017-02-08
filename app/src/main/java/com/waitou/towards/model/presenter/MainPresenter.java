package com.waitou.towards.model.presenter;

import android.support.v4.app.Fragment;

import com.waitou.net_library.helper.RxTransformerHelper;
import com.waitou.net_library.model.RequestParams;
import com.waitou.towards.ExtraValue;
import com.waitou.towards.model.MainActivity;
import com.waitou.towards.model.jokes.contract.MainContract;
import com.waitou.towards.model.jokes.fragment.joke.JokeContentFragment;
import com.waitou.towards.net.DataLoader;
import com.waitou.towards.net.SimpleErrorVerify;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroid.base.XPresent;

import static com.waitou.towards.ExtraValue.EXTRA_VALUE_0;
import static com.waitou.towards.ExtraValue.EXTRA_VALUE_1;

/**
 * Created by waitou on 17/1/27.
 */

public class MainPresenter extends XPresent<MainActivity> implements MainContract.MainPresenter {


    private MainContract.TextJokeView mTextJokeView;


    private List<Fragment> fragments = new ArrayList<>();

    public MainPresenter(MainContract.TextJokeView textJokeView) {
        this.mTextJokeView = textJokeView;
        mTextJokeView.setPresenter(this);
    }

    @Override
    public List<Fragment> getJokeFragmentList() {
        if (fragments.size() > 0) {
            return fragments;
        }
        fragments.add(JokeContentFragment.getInstance(EXTRA_VALUE_0, this));
        fragments.add(JokeContentFragment.getInstance(EXTRA_VALUE_1, this));
        return fragments;
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

}
