package com.waitou.widget_lib;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;


public class StateController extends FrameLayout {

    private static final int RES_NONE = -1;
    private int displayState = RES_NONE;
    private View loadingView, errorView, emptyView, contentView;
    private int loadingLayoutId, errorLayoutId, emptyLayoutId, contentLayoutId;
    private OnStateChangeListener stateChangeListener;

    public static final int STATE_LOADING = 0x1;
    public static final int STATE_ERROR = 0x2;
    public static final int STATE_EMPTY = 0x3;
    public static final int STATE_CONTENT = 0x4;

    public StateController(Context context) {
        this(context, null);
    }

    public StateController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupAttrs(context, attrs);
    }

    @SuppressLint("CustomViewStyleable")
    private void setupAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.widget_StateController);
        loadingLayoutId = typedArray.getResourceId(R.styleable.widget_StateController_widget_loadingLayoutId, RES_NONE);
        errorLayoutId = typedArray.getResourceId(R.styleable.widget_StateController_widget_errorLayoutId, RES_NONE);
        emptyLayoutId = typedArray.getResourceId(R.styleable.widget_StateController_widget_emptyLayoutId, RES_NONE);
        contentLayoutId = typedArray.getResourceId(R.styleable.widget_StateController_widget_contentLayoutId, RES_NONE);
        typedArray.recycle();
    }

    public void setDisplayState(int newState) {
        int oldState = displayState;
        if (newState != oldState) {
            switch (newState) {
                case STATE_LOADING:
                    if (loadingView == null && loadingLayoutId != RES_NONE) {
                        loadingView = inflate(getContext(), loadingLayoutId, null);
                        addView(loadingView);
                    }
                    notifyStateChange(oldState, newState, loadingView);
                    break;
                case STATE_ERROR:
                    if (errorView == null && errorLayoutId != RES_NONE) {
                        errorView = inflate(getContext(), errorLayoutId, null);
                        addView(errorView);
                    }
                    notifyStateChange(oldState, newState, errorView);
                    break;
                case STATE_EMPTY:
                    if (emptyView == null && emptyLayoutId != RES_NONE) {
                        emptyView = inflate(getContext(), emptyLayoutId, null);
                        addView(emptyView);
                    }
                    notifyStateChange(oldState, newState, emptyView);
                    break;
                case STATE_CONTENT:
                    if (contentView == null && contentLayoutId != RES_NONE) {
                        contentView = inflate(getContext(), contentLayoutId, null);
                        addView(contentView);
                    }
                    notifyStateChange(oldState, newState, contentView);
                    break;
            }
        }
    }

    private View getDisplayView(int oldState) {
        if (oldState == STATE_LOADING) return loadingView;
        if (oldState == STATE_ERROR) return errorView;
        if (oldState == STATE_EMPTY) return emptyView;
        return contentView;
    }

    private void notifyStateChange(int oldState, int newState, View enterView) {
        if (enterView != null) {
            displayState = newState;
            if (oldState != -1) {
                getStateChangeListener().onStateChange(oldState, newState);
                getStateChangeListener().animationState(getDisplayView(oldState), enterView);
            } else {
                enterView.setVisibility(VISIBLE);
                enterView.setAlpha(1);
            }
        }
    }

    public int getState() {
        return displayState;
    }

    public void showContent() {
        setDisplayState(STATE_CONTENT);
    }

    public void showEmpty() {
        setDisplayState(STATE_EMPTY);
    }

    public void showError() {
        setDisplayState(STATE_ERROR);
    }

    public void showLoading() {
        setDisplayState(STATE_LOADING);
    }

    public View getLoadingView() {
        return loadingView;
    }

    public View getEmptyView() {
        return emptyView;
    }

    public View getErrorView() {
        return errorView;
    }

    public View getContentView() {
        return contentView;
    }

    public StateController loadingView(View loadingView) {
        if (this.loadingView != null) {
            removeView(this.loadingView);
        }
        this.loadingView = loadingView;
        addView(this.loadingView);
        this.loadingView.setVisibility(GONE);
        return this;
    }

    public StateController errorView(View errorView) {
        if (this.errorView != null) {
            removeView(this.errorView);
        }
        this.errorView = errorView;
        addView(this.errorView);
        this.errorView.setVisibility(GONE);
        return this;
    }

    public StateController emptyView(View emptyView) {
        if (this.emptyView != null) {
            removeView(this.emptyView);
        }
        this.emptyView = emptyView;
        addView(this.emptyView);
        this.emptyView.setVisibility(GONE);
        return this;
    }

    public StateController contentView(View contentView) {
        if (this.contentView != null) {
            removeView(this.contentView);
        }
        this.contentView = contentView;
        addView(this.contentView);
        this.contentView.setVisibility(GONE);
        return this;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        SavedState savedState = new SavedState(parcelable);
        savedState.state = this.displayState;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.displayState = savedState.state;
        setDisplayState(this.displayState);
    }


    static class SavedState extends BaseSavedState {
        int state;

        SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            try {
                state = source.readInt();
            } catch (IllegalArgumentException e) {
                state = STATE_LOADING;
            }
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(state);
        }

        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };

    }

    public void registerStateChangeListener(OnStateChangeListener stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

    public OnStateChangeListener getStateChangeListener() {
        if (stateChangeListener == null) {
            stateChangeListener = getDefaultStateChangeListener();
        }
        return stateChangeListener;
    }

    private OnStateChangeListener getDefaultStateChangeListener() {
        return new SimpleStateChangeListener();
    }

    public interface OnStateChangeListener {
        void onStateChange(int oldState, int newState);

        void animationState(View exitView, View enterView);
    }

    public static class SimpleStateChangeListener implements OnStateChangeListener {

        @Override
        public void onStateChange(int oldState, int newState) {

        }

        @Override
        public void animationState(final View exitView, final View enterView) {
            AnimatorSet set = new AnimatorSet();
            final ObjectAnimator enter = ObjectAnimator.ofFloat(enterView, View.ALPHA, 1f);
            ObjectAnimator exit = ObjectAnimator.ofFloat(exitView, View.ALPHA, 0f);
            set.playTogether(enter, exit);
            set.setDuration(300);
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    enterView.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    exitView.setAlpha(1);
                    exitView.setVisibility(GONE);
                    checkView(enterView);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            set.start();
        }

        private void checkView(View enterView) {
            int visibleChild = 0;
            FrameLayout parent = (FrameLayout) enterView.getParent();
            int childCount = parent.getChildCount();
            for (int index = 0; index < childCount; index++) {
                if (parent.getChildAt(index).getVisibility() == VISIBLE) {
                    visibleChild++;
                }
            }
            if (visibleChild < 1) {
                enterView.setVisibility(VISIBLE);
                enterView.setAlpha(1);
            }
        }
    }
}
