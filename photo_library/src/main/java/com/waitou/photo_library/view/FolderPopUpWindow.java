package com.waitou.photo_library.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.PopupWindow;

import com.waitou.photo_library.R;
import com.waitou.photo_library.databinding.PopFolderBinding;
import com.waitou.wt_library.recycler.LayoutManagerUtli;

/**
 * Created by waitou on 17/4/8.
 * 文件弹框
 */

public class FolderPopUpWindow extends PopupWindow implements ViewTreeObserver.OnGlobalLayoutListener, View.OnClickListener {

    private PopFolderBinding mBinding;
    private int              margin;

    public FolderPopUpWindow(Context context, RecyclerView.Adapter adapter) {
        super(context);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.pop_folder, null, false);
        mBinding.list.setLayoutManager(LayoutManagerUtli.getVerticalLayoutManager(context));
        mBinding.list.setAdapter(adapter);
        mBinding.parent.setOnClickListener(this);
        mBinding.margin.setOnClickListener(this);
        setContentView(mBinding.getRoot());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);  //如果不设置，就是 AnchorView 的宽度
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true); // 设置允许在外点击消失
        setBackgroundDrawable(new ColorDrawable(0));
        setAnimationStyle(R.style.pop_style_fade);
    }

    private void enterAnimator() {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mBinding.list, "translationY", mBinding.list.getHeight(), 0);
        translationY.setDuration(400);
        translationY.setInterpolator(new AccelerateDecelerateInterpolator());
        translationY.start();
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        mBinding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(this);
        mBinding.getRoot().post(this::enterAnimator);
    }

    @Override
    public void dismiss() {
        mBinding.getRoot().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        exitAnimator();
    }

    private void exitAnimator() {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mBinding.list, "translationY", 0, mBinding.list.getHeight());
        translationY.setDuration(300);
        translationY.setInterpolator(new AccelerateDecelerateInterpolator());
        translationY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                FolderPopUpWindow.super.dismiss();
            }
        });
        translationY.start();
    }

    @Override
    public void onGlobalLayout() {
        int maxHeight = mBinding.getRoot().getHeight() * 5 / 8;
        int realHeight = mBinding.list.getHeight();
        ViewGroup.LayoutParams listParams = mBinding.list.getLayoutParams();
        listParams.height = realHeight > maxHeight ? maxHeight : realHeight;
        mBinding.list.setLayoutParams(listParams);
        ViewGroup.LayoutParams marginParams = mBinding.margin.getLayoutParams();
        marginParams.height = margin;
        mBinding.margin.setLayoutParams(marginParams);
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
