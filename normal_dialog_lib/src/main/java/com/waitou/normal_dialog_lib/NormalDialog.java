package com.waitou.normal_dialog_lib;

import android.content.DialogInterface;
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
 * DialogFragment生命周期
 * onCreate-->onCreateDialog-->onCreateView-->onViewCreated-->onStart
 */
public class NormalDialog extends DialogFragment {

    protected Float dimAmount;
    protected int   gravity;
    protected int   width;//宽度
    protected int   height;//高度

    private IDialogView iDialogView;
    private DialogInterface.OnDismissListener onDismissListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dl_Dialog_Tip);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return iDialogView != null ? iDialogView.getContentView(getActivity()) : new View(getActivity());
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
                window.setWindowAnimations(R.style.dl_anim_slide_share_from_bottom);
            }
            window.setAttributes(attributes);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    /**
     * 设置view
     */
    public NormalDialog setDialogView(IDialogView iDialogView) {
        this.iDialogView = iDialogView;
        return this;
    }

    public NormalDialog setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    /**
     * 屏幕外不可点击取消
     */
    public NormalDialog setOutCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        return this;
    }

    /**
     * 设置view的对接
     *
     * @param gravity {@link Gravity}
     */
    public NormalDialog setGravity(int gravity) {
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
    public NormalDialog setWidget(int width) {
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
    public NormalDialog setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * 设置
     * 调节灰色背景透明度[0-1]
     */
    public NormalDialog setDimAmount(float dimAmount) {
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
