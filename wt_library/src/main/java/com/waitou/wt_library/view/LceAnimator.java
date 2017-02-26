/*
 * Copyright 2015 Hannes Dorfmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.waitou.wt_library.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import com.waitou.wt_library.kit.Kits;


/**
 * Little helper class for animating content, error and loading view
 *
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
public class LceAnimator {

    /**
     * Show the loading view. No animations, because sometimes loading things is pretty fast (i.e.
     * retrieve data from memory cache).
     */
    public static void showLoading(final View loadingView, final View contentView, final View errorView, final View emptyView) {
        nullView(null, contentView, errorView, emptyView);
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * Shows the error view instead of the loading view
     */
    public static void showErrorView(final View loadingView, final View contentView, final View errorView, final View emptyView) {
        nullView(null, contentView, null, emptyView);
        // Not visible yet, so animate the view in
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator in = ObjectAnimator.ofFloat(errorView, "alpha", 1f);
        ObjectAnimator loadingOut = ObjectAnimator.ofFloat(loadingView, "alpha", 0f);

        set.playTogether(in, loadingOut);
        set.setDuration(200);

        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                errorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (loadingView != null) {
                    loadingView.setVisibility(View.GONE);
                    loadingView.setAlpha(1f); // For future showLoading calls
                }
            }
        });

        set.start();
    }

    public static void showEmptyView(final View loadingView, final View contentView,
                                     final View errorView, final View emptyView) {
        showErrorView(loadingView, contentView, emptyView, errorView);
    }

    /**
     * Display the content instead of the loadingView
     */
    public static void showContent(final View loadingView, final View contentView,
                                   final View errorView, View emptyView) {

        if (contentView.getVisibility() == View.VISIBLE) {
            // No Changing needed, because contentView is already visible
            nullView(loadingView, null, errorView, emptyView);
        } else {
            nullView(null, null, errorView, emptyView);
            int translateDp = 40;
            // Not visible yet, so animate the view in
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator contentFadeIn = ObjectAnimator.ofFloat(contentView, "alpha", 0f, 1f);
            ObjectAnimator contentTranslateIn = ObjectAnimator.ofFloat(contentView, "translationY",
                    Kits.Dimens.dip2px(loadingView.getContext(), translateDp), 0);

            ObjectAnimator loadingFadeOut = ObjectAnimator.ofFloat(loadingView, "alpha", 1f, 0f);
            ObjectAnimator loadingTranslateOut = ObjectAnimator.ofFloat(loadingView, "translationY", 0,
                    -Kits.Dimens.dip2px(loadingView.getContext(), translateDp));

            set.playTogether(contentFadeIn, contentTranslateIn, loadingFadeOut, loadingTranslateOut);
            set.setDuration(500);

            set.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    contentView.setTranslationY(0);
                    contentView.setVisibility(View.VISIBLE);
                    loadingView.setTranslationY(0);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingView.setVisibility(View.GONE);
                    loadingView.setAlpha(1f); // For future showLoading calls
                    loadingView.setTranslationY(0);
                    contentView.setTranslationY(0);
                }
            });

            set.start();
        }
    }

    public static void nullView(final View loadingView, final View contentView, final View errorView, final View emptyView) {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (contentView != null) {
            contentView.setVisibility(View.GONE);
        }
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }
}