package com.waitou.towards.model.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.os.SystemClock;

import java.util.Timer;
import java.util.TimerTask;

/**
 * auth aboom
 * date 2019/4/22
 */
public class VM extends ViewModel {

    private static final int ONE_SECOND = 1000;


    private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();

    LiveData<Boolean> map = Transformations.map(mElapsedTime, input -> input % 2 == 0);



    private long mInitialTime;

    public VM() {



        mInitialTime = SystemClock.elapsedRealtime();
        Timer timer = new Timer();

        // Update the elapsed time every second.
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000;
                // setValue() cannot be called from a background thread so post to main thread.
                mElapsedTime.postValue(newValue);
            }
        }, ONE_SECOND, ONE_SECOND);

    }

    public LiveData<Long> getElapsedTime() {
        return mElapsedTime;
    }


    public LiveData<Boolean> getMap() {
        return map;
    }
}
