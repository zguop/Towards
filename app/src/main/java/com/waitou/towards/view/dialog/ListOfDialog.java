package com.waitou.towards.view.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.waitou.towards.R;
import com.waitou.towards.databinding.IncludeMatchRecyclerViewBinding;

/**
 * Created by waitou on 17/3/27.
 * 存放列表的dialog
 */

public class ListOfDialog extends BaseDialog {

    private IncludeMatchRecyclerViewBinding mRecyclerViewBinding;

    public ListOfDialog(@NonNull Context context) {
        super(context);
        mRecyclerViewBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.include_match_recycler_view, null, false);
        setDialogContentView(mRecyclerViewBinding.getRoot());
    }

    public ListOfDialog setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerViewBinding.setManager(layoutManager);
        return this;
    }

    public ListOfDialog setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerViewBinding.setAdapter(adapter);
        return this;
    }

    public ListOfDialog setMaxContentView(int itemCount, int itemHeight) {
        ViewGroup.LayoutParams layoutParams = mRecyclerViewBinding.getRoot().getLayoutParams();
        int screenHeight = ScreenUtils.getScreenHeight();
        int screenWidth = ScreenUtils.getScreenWidth();
        int min = Math.min(screenHeight, screenWidth); //取屏幕最小值 减去dialog 头部 底部的高度 再多减去大概高度
        int maxHeight = min - SizeUtils.dp2px(140) - SizeUtils.dp2px(itemHeight);
        int planHeight = itemCount * SizeUtils.dp2px(itemHeight);// 原本高度
        if (planHeight > maxHeight) {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = maxHeight;
            mRecyclerViewBinding.getRoot().setLayoutParams(layoutParams);
        }
        return this;
    }
}
