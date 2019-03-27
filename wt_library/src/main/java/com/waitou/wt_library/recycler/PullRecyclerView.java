package com.waitou.wt_library.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.waitou.wt_library.R;
import com.waitou.wt_library.view.WTContentLayout;

/**
 * Created by waitou on 17/5/10.
 */

public class PullRecyclerView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {

    private int     padding;
    private int     paddingLeft;
    private int     paddingRight;
    private int     paddingTop;
    private int     paddingBottom;
    private int     scrollbarStyle;
    private int     backgroundColor;
    private boolean clipToPadding;
    private boolean scrollbarNone = false;

    public static final  int LOAD_MORE_ITEM_SLOP = 20;
    private static final int DEFAULT_PAGE_SIZE   = 2;
    private View              loadMoreView;      //加载更多控件
    private LoadMoreUIHandler loadMoreUIHandler;
    private boolean           isSetAdapter;
    private boolean loadMore = false; //是否加载更多
    private XRecyclerAdapter adapter;
    private int totalPage   = DEFAULT_PAGE_SIZE;
    private int currentPage = 1;
    private OnRefreshAndLoadMoreListener onRefreshAndLoadMoreListener;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView       recyclerView;

    public PullRecyclerView(Context context) {
        this(context, null);
    }

    public PullRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttrs(context, attrs);
        inflate(context, R.layout.rv_view_recycler_layout, this);
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
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
        recyclerView.addOnScrollListener(processMoreListener);
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        if (onRefreshAndLoadMoreListener != null) {
            onRefreshAndLoadMoreListener.onRefresh();
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        if (layout == null) {
            return;
        }
        recyclerView.setLayoutManager(layout);
        if (layout instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layout).getSpanCount();
            setSpanLookUp(layout, spanCount);
        }
        if (layout instanceof StaggeredGridLayoutManager) {
            int spanCount = ((StaggeredGridLayoutManager) layout).getSpanCount();
            setSpanLookUp(layout, spanCount);
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter == null || isSetAdapter) {
            return;
        }
        if (!(adapter instanceof XRecyclerAdapter)) {
            adapter = new XRecyclerAdapter(adapter);
        }
        recyclerView.setAdapter(adapter);
        isSetAdapter = true;

        final XRecyclerAdapter finalAdapter = (XRecyclerAdapter) adapter;
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                update();
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                update();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                update();
            }

            private void update() {
                int dataCount = finalAdapter.getDataCount();
                if (dataCount > 0) {
                    //数据大于0 并且是正在加载中，到这里表示加载完毕
                    if (loadMore) {
                        loadMoreCompleted();
                    }
                    notifyContent();
                } else {
                    if (finalAdapter.getHeaderSize() > 0 || finalAdapter.getFooterSize() > 0) {
                        if (loadMoreView != null) {
                            loadMoreView.setVisibility(GONE);
                        }
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        this.adapter = (XRecyclerAdapter) adapter;

        defaultLoadMoreView();
    }

    private RecyclerView.OnScrollListener processMoreListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (adapter == null || recyclerView.getLayoutManager() == null) return;

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                int totalCount = adapter.getItemCount();
                if (!loadMore && getLastVisibleItemPosition(recyclerView.getLayoutManager()) + LOAD_MORE_ITEM_SLOP > totalCount) {
                    if (totalPage > currentPage) {
                        loadMore = true;
                        //回调加载更多
                        if (onRefreshAndLoadMoreListener != null) {
                            onRefreshAndLoadMoreListener.onLoadMore(++currentPage);
                            swipeRefreshLayout.setEnabled(false);
                            if (loadMoreUIHandler != null) {
                                loadMoreUIHandler.onLoading();
                            }
                        }
                    } else {
                        loadMoreCompleted();
                    }
                } else {
                    swipeRefreshLayout.setEnabled(true);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    /**
     * 设置默认的加载更多view
     */
    private void defaultLoadMoreView() {
        SimpleLoadMoreFooter loadMoreFooter = new SimpleLoadMoreFooter(getContext());
        setLoadMoreView(loadMoreFooter);
        setLoadMoreUIHandler(loadMoreFooter);
    }

    /**
     * 加载完毕
     */
    private void loadMoreCompleted() {
        if (loadMoreUIHandler != null) {
            loadMoreUIHandler.onLoadFinish(totalPage > currentPage);
        }
        loadMore = false;
        swipeRefreshLayout.setEnabled(true);
    }

    /**
     * 获取最后一个位置
     */
    private int getLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int lastVisibleItemPosition = -1;
        if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
            //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            lastVisibleItemPosition = findMax(lastPositions);
        }
        return lastVisibleItemPosition;
    }

    private int getFirstVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int firstVisibleItemPosition = -1;
        if (layoutManager instanceof GridLayoutManager) {
            firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] lastScrollPositions = new int[staggeredGridLayoutManager.getSpanCount()];
            firstVisibleItemPosition = findMin(lastScrollPositions);
        }
        return firstVisibleItemPosition;
    }

    private void setSpanLookUp(RecyclerView.LayoutManager layoutManager, final int spanCount) {
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (adapter != null) {
                        return adapter.isHeaderOrFooter(position) ? spanCount : 1;
                    }
                    return GridLayoutManager.DEFAULT_SPAN_COUNT;
                }
            });
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) layoutManager).setSpanCount(spanCount);
        }
    }

    public void setOnRefreshAndLoadMoreListener(OnRefreshAndLoadMoreListener onRefreshAndLoadMoreListener) {
        this.onRefreshAndLoadMoreListener = onRefreshAndLoadMoreListener;
        swipeRefreshLayout.setEnabled(true);
    }

    public interface OnRefreshAndLoadMoreListener {
        void onRefresh();

        void onLoadMore(int page);
    }

    private void notifyContent() {
        if (getParent() != null && getParent() instanceof WTContentLayout) {
            ((WTContentLayout) getParent()).showContent();
        }
    }

    /**
     * 找到数组中的最大值
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 找到数组中最小值
     */
    private int findMin(int[] positions) {
        int min = Integer.MIN_VALUE;
        for (int value : positions) {
            if (value < min)
                min = value;
        }
        return min;
    }

    public void setDefaultPageSize() {
        setPageSize(currentPage, DEFAULT_PAGE_SIZE);
    }

    public void setPage(final int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(final int currentPage, final int totalPage) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }

    /**
     * 加载错误时调用 loading提示错误
     */
    public void loadMoreError(String s) {
        if (loadMoreUIHandler != null) {
            loadMoreUIHandler.onErrorMore(s);
        }
        loadMore = false;
    }

    /**
     * 设置加载更多的view
     */
    public void setLoadMoreView(View view) {
        if (loadMoreView != null && loadMoreView != view) {
            removeFooterView(view);
        }
        loadMoreView = view;
        addFooterView(view);
    }

    public void setLoadMoreUIHandler(LoadMoreUIHandler loadMoreUIHandler) {
        this.loadMoreUIHandler = loadMoreUIHandler;
    }

    public boolean removeFooterView(View view) {
        return view != null && adapter != null && adapter.removeFootView(view);
    }

    public boolean addFooterView(View view) {
        return view != null && adapter != null && adapter.addFootView(view);
    }
}
