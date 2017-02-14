package com.waitou.towards.model.jokes.contract;

import android.support.v4.app.Fragment;

import com.waitou.net_library.model.Displayable;
import com.waitou.towards.model.MainActivity;
import com.waitou.towards.model.jokes.fragment.joke.JokeContentFragment;
import com.waitou.wt_library.base.IView;
import com.waitou.wt_library.base.UIPresent;

import java.util.List;


/**
 * Created by waitou on 17/1/28.
 */

public interface MainContract {
    interface MainView {
    }

    interface HomeView extends IView<com.waitou.towards.model.presenter.MainPresenter> {

    }

    interface TextJokeView extends IView<MainPresenter> {
        JokeContentFragment getCurrentJokeFragment();

    }

    interface JokeContentView<T extends Displayable> {
        void showError(boolean isReload);

        void success(int page, List<T> info);
    }

    interface MainPresenter extends UIPresent<MainActivity> {
        List<Fragment> getHomeFragmentList();

        List<Fragment> getJokeFragmentList();

        void loadJokeData(int page, int type);

    }
}
