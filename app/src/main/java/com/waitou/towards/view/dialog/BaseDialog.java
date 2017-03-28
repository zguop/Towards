package com.waitou.towards.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.waitou.towards.R;
import com.waitou.towards.databinding.DialogBaseBinding;

/**
 * Created by waitou on 17/3/27.
 * dialog
 */

public class BaseDialog extends Dialog {

    private DialogBaseBinding mBaseBinding;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.Dialog_Tip);
        mBaseBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_base, null, false);
        setContentView(mBaseBinding.getRoot());
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp;
        if (dialogWindow != null) {
            lp = dialogWindow.getAttributes();
            dialogWindow.setGravity(Gravity.CENTER);
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
    }

    protected DialogBaseBinding getBaseBinding() {
        return mBaseBinding;
    }

    /**
     * 填充新布局到内容区域
     */
    protected void setDialogContentView(View view) {
        if (mBaseBinding.contentView.getChildCount() > 0) {
            mBaseBinding.contentView.removeAllViews();
        }
        mBaseBinding.contentView.addView(view);
    }

    /**
     * 设置标题内容
     */
    public BaseDialog setTitle(String titleDes) {
        if (toggleView(mBaseBinding.dialogTitle, titleDes)) {
            mBaseBinding.dialogTitle.setText(titleDes);
        }
        return this;
    }

    /**
     * 设置描述内容
     */
    public BaseDialog setContent(String des) {
        if (toggleView(mBaseBinding.contentTv, des)) {
            mBaseBinding.contentTv.setText(des);
        }
        return this;
    }

    /**
     * 设置content字体对齐方式 默认center_horizontal
     */
    public BaseDialog setContentGravity(int gravity) {
        mBaseBinding.contentTv.setGravity(gravity);
        return this;
    }


    /**
     * 设置底部左边按钮的文字,事件
     */
    public BaseDialog setCancel(String des, final OnDialogClickListener listener) {
        if (toggleView(mBaseBinding.btnCancel, des)) {
            mBaseBinding.btnCancel.setText(des);
        }
        showBtnDivider();
        mBaseBinding.btnCancel.setOnClickListener(v -> {
            dismiss();
            if (listener != null) {
                listener.dialogClick();
            }
        });
        return this;
    }

    public BaseDialog setOkColor(int color) {
        mBaseBinding.btnOk.setTextColor(color);
        return this;
    }

    /**
     * 设置底部右边按钮文字,事件
     */
    public BaseDialog setOk(String des, final OnDialogClickListener listener) {
        if (toggleView(mBaseBinding.btnOk, des)) {
            mBaseBinding.btnOk.setText(des);
        }
        showBtnDivider();
        mBaseBinding.btnOk.setOnClickListener(v -> {
            dismiss();
            if (listener != null) {
                listener.dialogClick();
            }
        });
        return this;
    }

    /**
     * 按下返回键不可关闭 false
     */
    public BaseDialog setDCancelable(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    /**
     * 屏幕外不可关闭 false
     */
    public BaseDialog setDCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        super.setCanceledOnTouchOutside(canceledOnTouchOutside);
        return this;
    }

    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }

    private void showBtnDivider() {
        mBaseBinding.btnDivider.setVisibility(isButtonNum() ? View.VISIBLE : View.GONE);
    }

    private boolean isButtonNum() {
        int cancelVisibility = mBaseBinding.btnCancel.getVisibility();
        int okVisibility = mBaseBinding.btnOk.getVisibility();
        if (cancelVisibility == View.GONE && okVisibility == View.GONE) {
            mBaseBinding.btnView.setVisibility(View.GONE);
            return false;
        }
        mBaseBinding.btnView.setVisibility(View.VISIBLE);
        return cancelVisibility == View.VISIBLE && okVisibility == View.VISIBLE;
    }

    private boolean toggleView(View view, Object obj) {
        if (obj == null) {
            view.setVisibility(View.GONE);
            return false;
        } else {
            view.setVisibility(View.VISIBLE);
            return true;
        }
    }

    public interface OnDialogClickListener {
        void dialogClick();
    }
}
