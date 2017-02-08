package com.waitou.towards.model.jokes.contract;

import android.support.v4.app.Fragment;

import com.waitou.net_library.model.Displayable;
import com.waitou.towards.model.MainActivity;
import com.waitou.towards.model.jokes.fragment.joke.JokeContentFragment;

import java.util.List;

import cn.droidlover.xdroid.base.IView;
import cn.droidlover.xdroid.base.UIPresent;

/**
 * Created by waitou on 17/1/28.
 */

public interface MainContract {
    interface MainView{
    }

    interface TextJokeView extends IView<MainPresenter> {
        JokeContentFragment getCurrentJokeFragment();

    }

    interface JokeContentView<T extends Displayable> {
        void showError(boolean isReload);

        void success(int page, List<T> info);
    }

    interface MainPresenter extends UIPresent<MainActivity> {
        List<Fragment> getJokeFragmentList();

        void loadJokeData(int page, int type);
    }
}
