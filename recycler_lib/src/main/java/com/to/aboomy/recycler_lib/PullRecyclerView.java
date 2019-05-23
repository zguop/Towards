package com.to.aboomy.recycler_lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * auth aboom
 * date 2019/3/18
 */
public class PullRecyclerView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView       recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView.Adapter adapter;
    private OnRefreshListener    onRefresh;
    private OnLoadMoreListener   onLoadMore;

    private int     currentPage = 1;
    private boolean isLastPage;
    private boolean loadMore;


    public PullRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public PullRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("CustomViewStyleable")
    public PullRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.rv_view_recycler_layout, this);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.rv_PullRecyclerView);
        boolean scrollbarNone = a.getBoolean(R.styleable.rv_PullRecyclerView_rv_scrollbarNone, Boolean.FALSE);
        a.recycle();
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setEnabled(Boolean.FALSE);
        swipeRefreshLayout.setColorSchemeColors(Color.MAGENTA, Color.YELLOW, Color.RED, Color.BLUE, Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = findViewById(R.id.recycler);
        if (scrollbarNone) {
            recyclerView.setVerticalScrollBarEnabled(Boolean.FALSE);
            recyclerView.setHorizontalScrollBarEnabled(Boolean.FALSE);
        }
    }

    public void distanceOffsetBar() {
        post(() -> {
            int distance = (int) (65 * Resources.getSystem().getDisplayMetrics().density + .5f) + swipeRefreshLayout.getProgressViewEndOffset();
            swipeRefreshLayout.setProgressViewEndTarget(Boolean.TRUE, distance);
        });
    }

    /**
     * 返回recyclerView让你设置一些额外的属性
     */
    public RecyclerView getContentView() {
        return recyclerView;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null)
            recyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        if (onLoadMore != null) {
            BaseQuickAdapter baseQuickAdapter = (BaseQuickAdapter) adapter;
            baseQuickAdapter.setEnableLoadMore(Boolean.FALSE);////这里的作用是防止下拉刷新的时候还可以上拉加载
        }
        if (onRefresh != null) {
            onRefresh.onRefresh();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (onLoadMore != null) {
            if (isLastPage) {
                BaseQuickAdapter baseQuickAdapter = (BaseQuickAdapter) adapter;
                baseQuickAdapter.loadMoreEnd();
                return;
            }
            loadMore = true;
            currentPage++;
            onLoadMore.onLoadMore(currentPage);
        }
    }

    public void showRefresh() {
        swipeRefreshLayout.setRefreshing(Boolean.TRUE);
    }

    public void loadComplete(boolean isLastPage) {
        this.isLastPage = isLastPage;
        if (loadMore) {
            BaseQuickAdapter baseQuickAdapter = (BaseQuickAdapter) adapter;
            loadMore = Boolean.FALSE;
            if (this.isLastPage) {
                baseQuickAdapter.loadMoreEnd();
            } else {
                baseQuickAdapter.loadMoreComplete();
            }
        } else {
            if (onLoadMore != null) {
                BaseQuickAdapter baseQuickAdapter = (BaseQuickAdapter) adapter;
                baseQuickAdapter.setEnableLoadMore(Boolean.TRUE);
            }
            swipeRefreshLayout.setRefreshing(Boolean.FALSE);
        }
    }

    public void loadMoreFail() {
        if (loadMore) {
            BaseQuickAdapter baseQuickAdapter = (BaseQuickAdapter) adapter;
            baseQuickAdapter.loadMoreFail();
            currentPage--;
        } else {
            currentPage = 1;
            if (onLoadMore != null) {
                BaseQuickAdapter baseQuickAdapter = (BaseQuickAdapter) adapter;
                baseQuickAdapter.setEnableLoadMore(Boolean.TRUE);
            }
            swipeRefreshLayout.setRefreshing(Boolean.FALSE);
        }
    }

    /**
     * 获取当前的页数
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 设置加载更多
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        if (!(adapter instanceof BaseQuickAdapter)) {
            throw new IllegalArgumentException("adapter no extends BaseQuickAdapter");
        }
        this.onLoadMore = onLoadMoreListener;
        BaseQuickAdapter baseQuickAdapter = (BaseQuickAdapter) adapter;
        baseQuickAdapter.setOnLoadMoreListener(this, recyclerView);
    }

    /**
     * 设置下啦刷新
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefresh = onRefreshListener;
        swipeRefreshLayout.setEnabled(Boolean.TRUE);
    }

    /**
     * 同时设置加载更多，下啦刷新
     */
    public void setRefreshAndLoadMoreListener(OnRefreshAndLoadMoreListener onRefreshAndLoadMoreListener) {
        setOnRefreshListener(onRefreshAndLoadMoreListener);
        setOnLoadMoreListener(onRefreshAndLoadMoreListener);
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int page);
    }

    public interface OnRefreshAndLoadMoreListener extends OnRefreshListener, OnLoadMoreListener {
    }
}
