package com.waitou.towards.model.activity;

import android.os.SystemClock;

import com.waitou.wt_library.base.XPresent;


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
