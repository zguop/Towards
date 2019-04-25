package com.waitou.widget_lib;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * auth aboom
 * date 2019-04-25
 */
public class BaseDialog extends DialogFragment implements IDialogView {

    private Float dimAmount;
    private int   gravity;
    private int   width;//宽度
    private int   height;//高度


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.widget_Dialog_Tip);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getContentView(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = this.width == 0 ? WindowManager.LayoutParams.WRAP_CONTENT : width;
            attributes.height = this.height == 0 ? WindowManager.LayoutParams.WRAP_CONTENT : height;
            attributes.gravity = gravity;
            if (dimAmount != null) {
                attributes.dimAmount = dimAmount;
            }
            if (gravity == Gravity.BOTTOM) {
                window.setWindowAnimations(R.style.widget_anim_slide_share_from_bottom);
            }
            window.setAttributes(attributes);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    /**
     * 屏幕外不可点击取消
     */
    public BaseDialog setOutCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        return this;
    }

    /**
     * 设置view的对接
     *
     * @param gravity {@link Gravity}
     */
    public BaseDialog setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    /**
     * 设置view宽
     *
     * @param width WindowManager.LayoutParams.WRAP_CONTENT  默认
     *              WindowManager.LayoutParams.MATCH_PARENT 可以传入
     *              60 固定值
     */
    public BaseDialog setWidget(int width) {
        this.width = width;
        return this;
    }

    /**
     * 设置view高
     *
     * @param height WindowManager.LayoutParams.WRAP_CONTENT  默认
     *               WindowManager.LayoutParams.MATCH_PARENT 可以传入
     *               60 固定值
     */
    public BaseDialog setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * 设置
     * 调节灰色背景透明度[0-1]
     */
    public BaseDialog setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public void show(FragmentManager fragmentManager) {
        if (this.isAdded()) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.remove(this).commit();
        }
        String simpleName = getClass().getSimpleName();
        super.show(fragmentManager, simpleName);
    }
}
