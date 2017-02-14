package com.waitou.wt_library.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.waitou.wt_library.R;
import com.waitou.wt_library.view.WTContentLayout;


/**
 * Created by wanglei on 2016/10/30.
 */

public class XPullRecyclerView extends FrameLayout implements XRecyclerView.StateCallback, SwipeRefreshLayout.OnRefreshListener {

    private int     padding;
    private int     paddingLeft;
    private int     paddingRight;
    private int     paddingTop;
    private int     paddingBottom;
    private int     scrollbarStyle;
    private int     backgroundColor;
    private boolean clipToPadding;
    private boolean scrollbarNone = false;

    private SwipeRefreshLayout swipeRefreshLayout;
    private XRecyclerView      recyclerView;


    public XPullRecyclerView(Context context) {
        this(context, null);
    }

    public XPullRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XPullRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupAttrs(context, attrs);
        inflateView(context);
    }

    private void setupAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XPullRecyclerView);

        backgroundColor = typedArray.getColor(R.styleable.XPullRecyclerView_recyclerBackgroundColor, Color.WHITE);
        padding = (int) typedArray.getDimension(R.styleable.XPullRecyclerView_recyclerPadding, -1.0f);
        paddingLeft = (int) typedArray.getDimension(R.styleable.XPullRecyclerView_recyclerPaddingLeft, 0.0f);
        paddingRight = (int) typedArray.getDimension(R.styleable.XPullRecyclerView_recyclerPaddingRight, 0.0f);
        paddingTop = (int) typedArray.getDimension(R.styleable.XPullRecyclerView_recyclerPaddingTop, 0.0f);
        paddingBottom = (int) typedArray.getDimension(R.styleable.XPullRecyclerView_recyclerPaddingBottom, 0.0f);
        scrollbarStyle = typedArray.getInt(R.styleable.XPullRecyclerView_recyclerScrollbarStyle, 2);
        clipToPadding = typedArray.getBoolean(R.styleable.XPullRecyclerView_recyclerClipToPadding, false);
        scrollbarNone = typedArray.getBoolean(R.styleable.XPullRecyclerView_recyclerScrollbarNone, false);

        typedArray.recycle();
    }

    private void inflateView(Context context) {
        inflate(context, R.layout.view_x_recycler_layout, this);
    }

    @Override
    protected void onFinishInflate() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (XRecyclerView) findViewById(R.id.recyclerView);

        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.x_red,
                R.color.x_blue,
                R.color.x_yellow,
                R.color.x_green
        );
        swipeRefreshLayout.setOnRefreshListener(this);
        if (padding != -1) {
            recyclerView.setPadding(padding, padding, padding, padding);
        } else {
            recyclerView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
        recyclerView.setClipToPadding(clipToPadding);

        if (scrollbarNone) {
            recyclerView.setVerticalScrollBarEnabled(false);
            recyclerView.setHorizontalScrollBarEnabled(false);
        } else {
            recyclerView.setScrollBarStyle(scrollbarStyle);
        }

        recyclerView.setBackgroundColor(backgroundColor);

        recyclerView.stateCallback(this);

        super.onFinishInflate();
    }

//    @Override
//    public void setDisplayState(int displayState) {
//        XRecyclerAdapter adapter = recyclerView.getAdapter();
//        if (adapter != null && adapter.getItemCount() > 0) {
//            super.setDisplayState(STATE_CONTENT);
//            return;
//        }
//        super.setDisplayState(displayState);
//    }
//
//    public void setDisplayState(int state, boolean isForce) {
//        if (isForce) {
//            super.setDisplayState(state);
//            return;
//        }
//        setDisplayState(state);
//    }
//
//    @Override
//    public void showEmpty() {
//        setDisplayState(STATE_EMPTY, true);
//    }
//

    public void showError(boolean isReload) {
        if (isReload) {
            if (getParent() != null && getParent() instanceof WTContentLayout) {
                ((WTContentLayout) getParent()).showError();
            }
        } else {
            recyclerView.loadMoreError("网络异常，请更换网络或稍后再试");
        }
    }

//    @Override
//    public void showError() {
//        setDisplayState(STATE_ERROR, true);
//    }
//
//    @Override
//    public void showLoading() {
//        setDisplayState(STATE_LOADING, true);
//    }

    @Override
    public void notifyContent() {
        if (getParent() != null && getParent() instanceof WTContentLayout) {
            ((WTContentLayout) getParent()).showContent();
        }
    }

    @Override
    public void refreshState(boolean isRefresh) {
        swipeRefreshLayout.setRefreshing(isRefresh);
    }

    @Override
    public void refreshEnabled(boolean isEnabled) {
        swipeRefreshLayout.setEnabled(isEnabled);
    }

    @Override
    public void onRefresh() {
        recyclerView.onRefresh();
    }

    public XRecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }
}
