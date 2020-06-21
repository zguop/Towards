package com.waitou.normal_dialog_lib;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.ref.WeakReference;

/**
 * auth aboom
 * DialogFragment生命周期
 * onCreate-->onCreateDialog-->onCreateView-->onViewCreated-->onStart
 * 屏幕旋转处理
 * android:configChanges="orientation|keyboardHidden|screenSize"
 */
public class NormalDialog extends DialogFragment {

    public static final String NORMAL_DIALOG_DIM_AMOUNT = "normal_dialog_dim_amount";
    public static final String NORMAL_DIALOG_GRAVITY = "normal_dialog_gravity";
    public static final String NORMAL_DIALOG_WIDTH = "normal_dialog_width";
    public static final String NORMAL_DIALOG_HEIGHT = "normal_dialog_height";
    public static final String NORMAL_DIALOG_ANIM_STYLE = "normal_dialog_anim_style";
    public static final String NORMAL_DIALOG_OUT_TOUCH = "normal_dialog_out_touch";
    public static final String NORMAL_DIALOG_DIALOG_VIEW = "normal_dialog_dialog_view";

    protected Bundle bundle = new Bundle();
    protected int animStyle;
    protected int width;//宽度
    protected int height;//高度
    protected int gravity;
    protected float dimAmount;
    protected boolean outTouchOutside;
    protected WeakReference<IDialogView> iDialogView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dl_Dialog_Tip);
        Bundle arguments = getArguments();
        if (arguments != null) {
            dimAmount = arguments.getFloat(NORMAL_DIALOG_DIM_AMOUNT, 0.5f);
            gravity = arguments.getInt(NORMAL_DIALOG_GRAVITY, Gravity.CENTER);
            width = arguments.getInt(NORMAL_DIALOG_WIDTH, WindowManager.LayoutParams.WRAP_CONTENT);
            height = arguments.getInt(NORMAL_DIALOG_HEIGHT, WindowManager.LayoutParams.WRAP_CONTENT);
            animStyle = arguments.getInt(NORMAL_DIALOG_ANIM_STYLE);
            outTouchOutside = arguments.getBoolean(NORMAL_DIALOG_OUT_TOUCH, true);
            iDialogView = new WeakReference<>(arguments.getParcelable(NORMAL_DIALOG_DIALOG_VIEW));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return iDialogView != null && iDialogView.get() != null ? iDialogView.get().getContentView(inflater, container) : new View(getActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        InnerDialog dialog = new InnerDialog(requireContext());
        dialog.setAnimStyle(animStyle);
        dialog.setDimAmount(dimAmount);
        dialog.setGravity(gravity);
        dialog.setHeight(height);
        dialog.setWidth(width);
        dialog.setCanceledOnTouchOutside(outTouchOutside);
        return dialog;
    }

    /**
     * 设置view
     */
    public NormalDialog setDialogView(IDialogView iDialogView) {
        bundle.putParcelable(NORMAL_DIALOG_DIALOG_VIEW, iDialogView);
        return this;
    }

    /**
     * 屏幕外不可点击取消 屏幕内都不可以点击取消
     */
    public NormalDialog setOutCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        return this;
    }

    /**
     * 设置屏幕内点击阴影取消
     */
    public NormalDialog setOutTouchOutside(boolean touchOutside) {
        bundle.putBoolean(NORMAL_DIALOG_OUT_TOUCH, touchOutside);
        return this;
    }

    /**
     * 设置view的对接
     *
     * @param gravity {@link Gravity}
     */
    public NormalDialog setGravity(int gravity) {
        bundle.putInt(NORMAL_DIALOG_GRAVITY, gravity);
        return this;
    }

    /**
     * 设置view宽
     *
     * @param width WindowManager.LayoutParams.WRAP_CONTENT  默认
     *              WindowManager.LayoutParams.MATCH_PARENT 可以传入
     *              60 固定值
     */
    public NormalDialog setWidth(int width) {
        bundle.putInt(NORMAL_DIALOG_WIDTH, width);
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
        bundle.putInt(NORMAL_DIALOG_HEIGHT, height);
        return this;
    }

    /**
     * 设置
     * 调节灰色背景透明度[0-1]
     */
    public NormalDialog setDimAmount(float dimAmount) {
        bundle.putFloat(NORMAL_DIALOG_DIM_AMOUNT, dimAmount);
        return this;
    }

    /**
     * 设置动画
     *
     * @param animStyle style主题默认 {@link values/styles.xml:25}
     */
    public NormalDialog setAnimStyle(@StyleRes int animStyle) {
        bundle.putInt(NORMAL_DIALOG_ANIM_STYLE, animStyle);
        return this;
    }

    public void show(FragmentManager fragmentManager) {
        String simpleName = getClass().getSimpleName();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment old = fragmentManager.findFragmentByTag(simpleName);
        if (old != null) {
            ft.remove(old).commitAllowingStateLoss();
        }
        setArguments(bundle);
        ft.add(this, simpleName);
        ft.commitAllowingStateLoss();
    }
}
