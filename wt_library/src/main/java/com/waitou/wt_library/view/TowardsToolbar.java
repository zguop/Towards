package com.waitou.wt_library.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.waitou.wt_library.R;
import com.waitou.wt_library.databinding.ToolbarContentBinding;


/**
 * Created by waitou on 17/1/26.
 * bar
 */

public class TowardsToolbar extends Toolbar {

    private ToolbarContentBinding mBinding;

    public TowardsToolbar(Context context) {
        this(context, null);
    }

    public TowardsToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TowardsToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.toolbar_content, null, false);
        addView(mBinding.getRoot());
    }

    /**
     * 初始化一个标题
     *
     * @param title 标题
     */
    public void initMenuActionBar(CharSequence title) {
        initMenuActionBar(title, null, null);
    }

    /**
     * 初始化标题 文字菜单
     *
     * @param title    标题
     * @param menuText 菜单文字
     * @param listener 菜单点击事件
     */
    public void initMenuActionBar(CharSequence title, CharSequence menuText, OnClickListener listener) {
        if (mBinding != null) {
            if (mBinding.leftImg.getDrawable() == null) {
                setBackListener(R.drawable.back_white, v -> ((AppCompatActivity) getContext()).onBackPressed());
            }
            if (title != null) {
                mBinding.title.setText(title);
            }
            if (!TextUtils.isEmpty(menuText)) {
                mBinding.textMenu.setVisibility(VISIBLE);
                mBinding.textMenu.setText(menuText);
                if (listener != null) {
                    mBinding.textMenu.setOnClickListener(listener);
                }
            }
        }
    }

    /**
     * 初始化标题 图标菜单
     *
     * @param title    标题
     * @param menuIcon 菜单图片id
     * @param listener 菜单点击事件
     */
    public void initIconActionBar(CharSequence title, int menuIcon, OnClickListener listener) {
        if (mBinding != null) {
            if (mBinding.leftImg.getDrawable() == null) {
                setBackListener(R.drawable.back_white, v -> ((AppCompatActivity) getContext()).onBackPressed());
            }
            if (title != null) {
                mBinding.title.setText(title);
            }
            if (menuIcon != 0) {
                mBinding.iconMenu.setVisibility(VISIBLE);
                mBinding.iconMenu.setImageResource(menuIcon);
                if (listener != null) {
                    mBinding.textMenu.setOnClickListener(listener);
                }
            }
        }
    }

    /**
     * 设置返回键 图标 及点击事件
     *
     * @param resId    图片id
     * @param listener 左侧点击事件
     */
    public void setBackListener(@DrawableRes int resId, OnClickListener listener) {
        if (resId != 0) {
            mBinding.leftImg.setImageResource(resId);
        }
        mBinding.leftImg.setOnClickListener(listener);
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(CharSequence title) {
        if (title != null) {
            mBinding.title.setText(title);
        }
    }

    /**
     * 设置右侧菜单文字
     */
    public void setRightText(CharSequence rightText) {
        if (mBinding.textMenu.getVisibility() == VISIBLE && rightText != null) {
            mBinding.textMenu.setText(rightText);
        }
    }

    /**
     * 获取标题
     */
    public String getTitle() {
        return mBinding.title.getText().toString();
    }

    public void formTitle() {
        fromCustomMenuView(null, 0);
    }

    /**
     * 初始化文字菜单自定义view代替标题 多次使用只会叠加view 的个数
     *
     * @param dataBinding view
     */
    public void fromCustomMenuView(ViewDataBinding dataBinding, int bindingKey) {
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
        if (mBinding != null) {
            if (dataBinding != null) {
                View root = dataBinding.getRoot();
                if (mBinding.barContent.getChildCount() > 1) {
                    boolean isAddBar = false;
                    for (int i = 0; i < mBinding.barContent.getChildCount(); i++) {
                        View childAt = mBinding.barContent.getChildAt(i);
                        Object tag = childAt.getTag();
                        if (tag != null && bindingKey == (int) tag) {
                            childAt.setVisibility(VISIBLE);
                            isAddBar = true;
                        } else {
                            childAt.setVisibility(GONE);
                        }
                    }
                    if (!isAddBar) {
                        root.setTag(bindingKey);
                        mBinding.barContent.addView(root);
                    }
                } else {
                    root.setTag(bindingKey);
                    mBinding.barContent.addView(root);
                    mBinding.title.setVisibility(GONE);
                }
            } else {
                for (int i = 0; i < mBinding.barContent.getChildCount(); i++) {
                    View childAt = mBinding.barContent.getChildAt(i);
                    Object tag = childAt.getTag();
                    if (tag == null) {
                        childAt.setVisibility(VISIBLE);
                    } else {
                        childAt.setVisibility(GONE);
                    }
                }
            }
        }
    }
}
