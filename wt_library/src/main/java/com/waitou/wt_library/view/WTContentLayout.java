package com.waitou.wt_library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.waitou.wt_library.R;


/**
 * Created by waitou on 17/1/5.
 * 包含4种状态布局的layout
 */

public class WTContentLayout extends FrameLayout {

    private static final int RES_NONE = -1;

    private int loadingLayoutId;
    private int errorLayoutId;
    private int emptyLayoutId;
    private int contentLayoutId;

    public static final int STATE_LOADING = 0x1;
    public static final int STATE_ERROR   = 0x2;
    public static final int STATE_EMPTY   = 0x3;
    public static final int STATE_CONTENT = 0x4;

    private View loadingView;
    private View errorView;
    private View emptyView;
    private View contentView;

    public WTContentLayout(Context context) {
        this(context, null);
    }

    public WTContentLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WTContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WTContentLayout);
        loadingLayoutId = typedArray.getResourceId(R.styleable.WTContentLayout_wt_loadingLayoutId, RES_NONE);
        errorLayoutId = typedArray.getResourceId(R.styleable.WTContentLayout_wt_errorLayoutId, RES_NONE);
        emptyLayoutId = typedArray.getResourceId(R.styleable.WTContentLayout_wt_emptyLayoutId, RES_NONE);
        contentLayoutId = typedArray.getResourceId(R.styleable.WTContentLayout_wt_contentLayoutId, RES_NONE);
        typedArray.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (loadingLayoutId != RES_NONE) {
            loadingView = inflate(getContext(), loadingLayoutId, null);
            addParent(loadingView);
        }
        if (errorLayoutId != RES_NONE) {
            errorView = inflate(getContext(), errorLayoutId, null);
            addParent(errorView);
        }
        if (emptyLayoutId != RES_NONE) {
            emptyView = inflate(getContext(), emptyLayoutId, null);
            addParent(emptyView);
        }
        if (contentLayoutId != RES_NONE) {
            contentView = inflate(getContext(), contentLayoutId, null);
            addParent(contentView);
        }
        LceAnimator.nullView(loadingView, contentView, errorView, emptyView);
    }

    protected void setDisplayState(int displayState) {
        if (displayState == STATE_LOADING) {
            LceAnimator.showLoading(loadingView, contentView, errorView, emptyView);
        }

        if (displayState == STATE_ERROR) {
            LceAnimator.showErrorView(loadingView, contentView, errorView, emptyView);
        }

        if (displayState == STATE_EMPTY) {
            LceAnimator.showEmptyView(loadingView, contentView, errorView, emptyView);
        }

        if (displayState == STATE_CONTENT) {
            LceAnimator.showContent(loadingView, contentView, errorView, emptyView);
        }
    }

    protected void bindView(View child, int state) {
        if (child != null) {
            if (state == STATE_LOADING) {
                if (loadingView != null) {
                    removeView(loadingView);
                }
                loadingView = child;
            }
            if (state == STATE_EMPTY) {
                if (emptyView != null) {
                    removeView(emptyView);
                }
                emptyView = child;
            }
            if (state == STATE_ERROR) {
                if (errorView != null) {
                    removeView(errorView);
                }
                errorView = child;
            }
            if (state == STATE_CONTENT) {
                if (contentView != null) {
                    removeView(contentView);
                }
                contentView = child;
            }
            addParent(child);
        }
    }

    protected void addParent(View view) {
        if (view.getParent() == null) {
            addView(view);
        }
    }

    public WTContentLayout addContentView(View contentView) {
        bindView(contentView, STATE_CONTENT);
        return this;
    }

    public WTContentLayout addLoadingView(View loadingView) {
        bindView(loadingView, STATE_LOADING);
        return this;
    }

    public WTContentLayout addErrorView(View errorView) {
        bindView(errorView, STATE_ERROR);
        return this;
    }

    public WTContentLayout addEmptyView(View emptyView) {
        bindView(emptyView, STATE_EMPTY);
        return this;
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

    public View getEmptyView() {
        return emptyView;
    }

    public View getLoadingView() {
        return loadingView;
    }

    public View getErrorView() {
        return errorView;
    }

    public View getContentView() {
        return contentView;
    }
}
