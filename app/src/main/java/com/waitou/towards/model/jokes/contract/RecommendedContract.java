package com.waitou.towards.model.jokes.contract;

/**
 * Created by waitou on 17/1/9.
 */
public interface RecommendedContract {

    interface RecommendedView {
        void showLoading();

        void showContent();

        void setText(String text);
    }

    interface IRecommendedPresenter  {
        void getData();
    }
}
