package com.waitou.towards.model.gallery.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.to.aboomy.utils_lib.AlertToast;
import com.to.aboomy.utils_lib.USize;
import com.waitou.wt_library.kit.UImage;

import io.reactivex.disposables.Disposable;

import static com.waitou.wt_library.kit.UImage.scale;


public class CardScaleHelper {
    private RecyclerView mRecyclerView;
    private Context      mContext;

    private float mScale             = 0.9f; // 两边视图scale
    private int   mPagePadding       = 15; // 卡片的padding, 卡片间的距离等于2倍的mPagePadding
    private int   mShowLeftCardWidth = 15;   // 左边卡片显示大小

    private int mCardWidth; // 卡片宽度
    private int mOnePageWidth; // 滑动一页的距离
    private int mCardGalleryWidth;

    private int mCurrentItemPos;
    private int mCurrentItemOffset;

    private int mLastPos = -1;

    private CardLinearSnapHelper mLinearSnapHelper = new CardLinearSnapHelper();
    private Disposable mSubscribe;


    public void attachToRecyclerView(final RecyclerView recyclerView) {
        // 开启log会影响滑动体验, 调试时才开启
        this.mRecyclerView = recyclerView;
        this.mContext = mRecyclerView.getContext();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLinearSnapHelper.mNoNeedToScroll = mCurrentItemOffset == 0 || mCurrentItemOffset == getDestItemOffset(mRecyclerView.getAdapter().getItemCount() - 1);
                    notifyBackgroundChange();
                } else {
                    mLinearSnapHelper.mNoNeedToScroll = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // dx>0则表示右滑, dx<0表示左滑, dy<0表示上滑, dy>0表示下滑
                mCurrentItemOffset += dx;
                computeCurrentItemPos();
                onScrolledChangedCallback();
            }
        });

        initWidth();
        mLinearSnapHelper.attachToRecyclerView(mRecyclerView);
        //第一次进入延迟调用 等待RecyclerView列表初始化完成
        mRecyclerView.postDelayed(this::notifyBackgroundChange, 500);
    }

    private void notifyBackgroundChange() {
        if (mLastPos == getCurrentItemPos()) {
            return;
        }
        mLastPos = getCurrentItemPos();
        startSwitchBackground();
    }

    private View getPositionView() {
        View positionView = mRecyclerView.getLayoutManager().findViewByPosition(mLastPos);
        if (positionView != null) {
            return ((ViewGroup) positionView).getChildAt(0);
        }
        return null;
    }

    private void startSwitchBackground() {
        View positionView = getPositionView();
        if (positionView != null) {
            positionView.setDrawingCacheEnabled(true);
            Bitmap drawingCache = positionView.getDrawingCache();
            UImage.startSwitchBackgroundAnim(mRecyclerView, UImage.fastBlur(drawingCache, 1, 25));
            positionView.setDrawingCacheEnabled(false);
        }
    }

    public void saveImageToGallery() {
        View positionView = getPositionView();
        if (positionView != null) {
            positionView.setDrawingCacheEnabled(true);
            Bitmap scale = scale(positionView.getDrawingCache(), USize.getDeviceWidth(), USize.getDeviceHeight());
            UImage.saveImageToGallery(mContext, scale);
            AlertToast.show("图片成功保存到相册O(∩_∩)O~");
            positionView.setDrawingCacheEnabled(false);
        }
    }

    /**
     * 初始化卡片宽度
     */
    private void initWidth() {
        mRecyclerView.post(() -> {
            mCardGalleryWidth = mRecyclerView.getWidth();
            mCardWidth = mCardGalleryWidth - USize.dip2pxInt( mPagePadding + mShowLeftCardWidth) * 2;
            mOnePageWidth = mCardWidth;
            notifyChangeWidth();
        });
    }

    public void notifyChangeWidth() {
        mRecyclerView.smoothScrollToPosition(mCurrentItemPos);
        onScrolledChangedCallback();
    }

    public void setCurrentItemPos(int currentItemPos) {
        this.mCurrentItemPos = currentItemPos;
    }

    public int getCurrentItemPos() {
        return mCurrentItemPos;
    }

    private int getDestItemOffset(int destPos) {
        return mOnePageWidth * destPos;
    }

    /**
     * 计算mCurrentItemOffset
     */
    private void computeCurrentItemPos() {
        if (mOnePageWidth <= 0) return;
        boolean pageChanged = false;
        // 滑动超过一页说明已翻页

        Log.e("aa" , "mCurrentItemOffset" + mCurrentItemOffset + "mCurrentItemPos " + mCurrentItemPos + " mOnePageWidth " + mOnePageWidth);
        if (Math.abs(mCurrentItemOffset - mCurrentItemPos * mOnePageWidth) >= mOnePageWidth) {
            pageChanged = true;
        }
        if (pageChanged) {
            mCurrentItemPos = mCurrentItemOffset / mOnePageWidth;
        }
    }

    /**
     * RecyclerView位移事件监听, view大小随位移事件变化
     */
    private void onScrolledChangedCallback() {

        int offset = mCurrentItemOffset - mCurrentItemPos * mOnePageWidth;
        float percent = (float) Math.max(Math.abs(offset) * 1.0 / mOnePageWidth, 0.0001);
        View leftView = null;
        View currentView;
        View rightView = null;
        if (mCurrentItemPos > 0) {
            leftView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos - 1);
        }
        currentView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos);

        if (mCurrentItemPos < mRecyclerView.getAdapter().getItemCount() - 1) {
            rightView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos + 1);
        }


        if (leftView != null) {
            // y = (1 - mScale)x + mScale
            leftView.setScaleY((1 - mScale) * percent + mScale);
        }
        if (currentView != null) {
            // y = (mScale - 1)x + 1
            currentView.setScaleY((mScale - 1) * percent + 1);
        }
        if (rightView != null) {
            // y = (1 - mScale)x + mScale
            rightView.setScaleY((1 - mScale) * percent + mScale);
        }
    }

    public void setScale(float scale) {
        mScale = scale;
    }

    public void setPagePadding(int pagePadding) {
        mPagePadding = pagePadding;
    }

    public void setShowLeftCardWidth(int showLeftCardWidth) {
        mShowLeftCardWidth = showLeftCardWidth;
    }
}
