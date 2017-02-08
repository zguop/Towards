package com.waitou.towards.model.presenter;

import android.os.SystemClock;

import com.waitou.towards.model.jokes.activity.RecommendedActivity;
import com.waitou.towards.model.jokes.contract.RecommendedContract;

import cn.droidlover.xdroid.base.XPresent;

/**
 * Created by waitou on 17/1/9.
 */

public class RecommendedPresenter extends XPresent<RecommendedActivity> implements RecommendedContract.IRecommendedPresenter {

    @Override
    public void start() {
        new Thread(() -> {
            getV().showLoading();
            SystemClock.sleep(2000);
            getV().showContent();

        }).start();
    }

    @Override
    public void getData() {

    }
}
