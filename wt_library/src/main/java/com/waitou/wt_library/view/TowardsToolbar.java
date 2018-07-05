package com.waitou.wt_library.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        if (!(context instanceof Activity)) {
            throw new IllegalArgumentException("Context 建议使用 Activity类型的");
        }
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.toolbar_content, this, true);
    }

    /**
     * 初始化标题栏
     */
    public DoubleClickTextView initializeHeaderNo(String header) {
        mBinding.title.setVisibility(View.VISIBLE);
        mBinding.title.setText(header);
        return mBinding.title;
    }

    /**
     * 设置左侧侧图标
     *
     * @param resId    资源id
     * @param listener 点击事件
     * @return ImageView
     */
    public ImageView setLeftIcon(int resId, View.OnClickListener listener) {
        ImageView leftImg = mBinding.leftImg;
        leftImg.setVisibility(View.VISIBLE);
        leftImg.setImageResource(resId);
        if (listener != null) {
            mBinding.leftClick.setOnClickListener(listener);
        }
        return leftImg;
    }

    /**
     * 设置右侧文字
     *
     * @param leftTxt  文字描述
     * @param listener 点击事件
     * @return setRightText
     */
    public TextView setLeftText(String leftTxt, View.OnClickListener listener) {
        mBinding.leftTxt.setVisibility(View.VISIBLE);
        mBinding.leftTxt.setText(leftTxt);
        if (listener != null) {
            mBinding.leftClick.setOnClickListener(listener);
        }
        return mBinding.leftTxt;
    }

    /**
     * 初始化标题栏
     *
     * @param header 标题文字
     */
    public TextView initializeHeader(String header) {
        return initializeHeader(header, v -> {
            Activity activity = (Activity) getContext();
            if (!activity.isFinishing())
                activity.onBackPressed();
        });
    }

    /**
     * 初始化标题栏
     *
     * @param header   标题文字
     * @param listener 自定义左侧图标返回事件
     */
    public TextView initializeHeader(String header, View.OnClickListener listener) {
        mBinding.leftClick.setVisibility(View.VISIBLE);
        mBinding.leftClick.setOnClickListener(listener);
        mBinding.title.setVisibility(View.VISIBLE);
        mBinding.title.setText(header);
        return mBinding.title;
    }

    /**
     * 设置右侧图标
     *
     * @param resId    资源id
     * @param listener 点击事件
     * @return ImageView
     */
    public ImageView setRightIcon(int resId, View.OnClickListener listener) {
        mBinding.iconMenu.setVisibility(View.VISIBLE);
        mBinding.iconMenu.setImageResource(resId);
        if (listener != null) {
            mBinding.rightClick.setOnClickListener(listener);
        }
        return mBinding.iconMenu;
    }

    /**
     * 设置右侧文字
     *
     * @param rightTxt 文字描述
     * @param listener 点击事件
     * @return setRightText
     */
    public TextView setRightText(String rightTxt, View.OnClickListener listener) {
        mBinding.textMenu.setVisibility(View.VISIBLE);
        mBinding.textMenu.setText(rightTxt);
        if (listener != null) {
            mBinding.rightClick.setOnClickListener(listener);
        }
        return  mBinding.textMenu;
    }

    /**
     * 设置右边菜单icon
     */
    public View[] setRightTextIcon(int resId, String leftTxt, View.OnClickListener listener) {
        TextView textView = setRightText(leftTxt, null);
        ImageView imageView = setRightIcon(resId, listener);
        return new View[]{textView, imageView};
    }

    /**
     * 设置右边菜单icon
     */
    public View[] setLeftTextIcon(int resId, String leftTxt, View.OnClickListener listener) {
        TextView textView = setLeftText(leftTxt, null);
        ImageView imageView = setLeftIcon(resId, listener);
        return new View[]{textView, imageView};
    }

    /**
     * 自定义标题栏view
     */
    public void customTitleView(ViewDataBinding dataBinding) {
        View showView = dataBinding.getRoot();
        int showViewHashCode = showView.hashCode();
        boolean flag = false;
        int childCount = mBinding.barContent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mBinding.barContent.getChildAt(i);
            int childHashCode = childAt.hashCode();
            if(showViewHashCode == childHashCode){
                childAt.setVisibility(VISIBLE);
                flag = true;
            }else{
                childAt.setVisibility(GONE);
            }
        }
        if(!flag){
            showView.setVisibility(VISIBLE);
            mBinding.barContent.addView(showView);
        }
    }
//    public void fromCustomMenuView(ViewDataBinding dataBinding, int bindingKey) {
//        if (getVisibility() != VISIBLE) {
//            setVisibility(VISIBLE);
//        }
//        if (mBinding != null) {
//            if (dataBinding != null) {
//                View root = dataBinding.getRoot();
//                if (mBinding.barContent.getChildCount() > 1) {
//                    boolean isAddBar = false;
//                    for (int i = 0; i < mBinding.barContent.getChildCount(); i++) {
//                        View childAt = mBinding.barContent.getChildAt(i);
//                        Object tag = childAt.getTag();
//                        if (tag != null && bindingKey == (int) tag) {
//                            childAt.setVisibility(VISIBLE);
//                            isAddBar = true;
//                        } else {
//                            childAt.setVisibility(GONE);
//                        }
//                    }
//                    if (!isAddBar) {
//                        root.setTag(bindingKey);
//                        mBinding.barContent.addView(root);
//                    }
//                } else {
//                    root.setTag(bindingKey);
//                    mBinding.barContent.addView(root);
//                    mBinding.title.setVisibility(GONE);
//                }
//            } else {
//                for (int i = 0; i < mBinding.barContent.getChildCount(); i++) {
//                    View childAt = mBinding.barContent.getChildAt(i);
//                    Object tag = childAt.getTag();
//                    if (tag == null) {
//                        childAt.setVisibility(VISIBLE);
//                    } else {
//                        childAt.setVisibility(GONE);
//                    }
//                }
//            }
//        }
//    }

//
//    /**
//     * 初始化一个标题
//     *
//     * @param title 标题
//     */
//    public void initMenuActionBar(CharSequence title) {
//        initMenuActionBar(title, null, null);
//    }
//
//    /**
//     * 初始化标题 文字菜单
//     *
//     * @param title    标题
//     * @param menuText 菜单文字
//     * @param listener 菜单点击事件
//     */
//    public void initMenuActionBar(CharSequence title, CharSequence menuText, OnClickListener listener) {
//        if (mBinding != null) {
//            if (mBinding.leftImg.getDrawable() == null) {
//                setBackListener(R.drawable.back_white, v -> ((AppCompatActivity) getContext()).onBackPressed());
//            }
//            if (title != null) {
//                mBinding.title.setText(title);
//            }
//            if (!TextUtils.isEmpty(menuText)) {
//                mBinding.textMenu.setVisibility(VISIBLE);
//                mBinding.textMenu.setText(menuText);
//                if (listener != null) {
//                    mBinding.textMenu.setOnClickListener(listener);
//                }
//            }
//        }
//    }
//
//    /**
//     * 初始化标题 图标菜单
//     *
//     * @param title    标题
//     * @param menuIcon 菜单图片id
//     * @param listener 菜单点击事件
//     */
//    public void initIconActionBar(CharSequence title, int menuIcon, OnClickListener listener) {
//        if (mBinding != null) {
//            if (mBinding.leftImg.getDrawable() == null) {
//                setBackListener(R.drawable.base_svg_ic_back, v -> ((AppCompatActivity) getContext()).onBackPressed());
//            }
//            if (title != null) {
//                mBinding.title.setText(title);
//            }
//            if (menuIcon != 0) {
//                mBinding.iconMenu.setVisibility(VISIBLE);
//                mBinding.iconMenu.setImageResource(menuIcon);
//                if (listener != null) {
//                    mBinding.textMenu.setOnClickListener(listener);
//                }
//            }
//        }
//    }
//
//    /**
//     * 设置返回键 图标 及点击事件
//     *
//     * @param resId    图片id
//     * @param listener 左侧点击事件
//     */
//    public void setBackListener(@DrawableRes int resId, OnClickListener listener) {
//        if (resId != 0) {
//            mBinding.leftImg.setImageResource(resId);
//        }
//        mBinding.leftImg.setOnClickListener(listener);
//    }
//
//    /**
//     * 设置标题
//     *
//     * @param title 标题
//     */
//    public void setTitle(CharSequence title) {
//        if (title != null) {
//            mBinding.title.setText(title);
//        }
//    }
//
//    /**
//     * 设置右侧菜单文字
//     */
//    public void setRightText(CharSequence rightText) {
//        if (mBinding.textMenu.getVisibility() == VISIBLE && rightText != null) {
//            mBinding.textMenu.setText(rightText);
//        }
//    }
//
//    /**
//     * 设置右边图标
//     */
//    public ImageView setRightIcon(int resId, View.OnClickListener listener) {
//        ImageView rightImg = findViewById(R.id.icon_menu);
//        rightImg.setVisibility(View.VISIBLE);
//        rightImg.setImageResource(resId);
//        if (listener != null) {
//            rightImg.setOnClickListener(listener);
//        }
//        return rightImg;
//    }
//
//    /**
//     * 获取标题
//     */
//    public String getTitle() {
//        return mBinding.title.getText().toString();
//    }
//
//    public void formTitle() {
//        fromCustomMenuView(null, 0);
//    }
//
//    /**
//     * 初始化文字菜单自定义view代替标题 多次使用只会叠加view 的个数
//     *
//     * @param dataBinding view
//     */
//    public void fromCustomMenuView(ViewDataBinding dataBinding, int bindingKey) {
//        if (getVisibility() != VISIBLE) {
//            setVisibility(VISIBLE);
//        }
//        if (mBinding != null) {
//            if (dataBinding != null) {
//                View root = dataBinding.getRoot();
//                if (mBinding.barContent.getChildCount() > 1) {
//                    boolean isAddBar = false;
//                    for (int i = 0; i < mBinding.barContent.getChildCount(); i++) {
//                        View childAt = mBinding.barContent.getChildAt(i);
//                        Object tag = childAt.getTag();
//                        if (tag != null && bindingKey == (int) tag) {
//                            childAt.setVisibility(VISIBLE);
//                            isAddBar = true;
//                        } else {
//                            childAt.setVisibility(GONE);
//                        }
//                    }
//                    if (!isAddBar) {
//                        root.setTag(bindingKey);
//                        mBinding.barContent.addView(root);
//                    }
//                } else {
//                    root.setTag(bindingKey);
//                    mBinding.barContent.addView(root);
//                    mBinding.title.setVisibility(GONE);
//                }
//            } else {
//                for (int i = 0; i < mBinding.barContent.getChildCount(); i++) {
//                    View childAt = mBinding.barContent.getChildAt(i);
//                    Object tag = childAt.getTag();
//                    if (tag == null) {
//                        childAt.setVisibility(VISIBLE);
//                    } else {
//                        childAt.setVisibility(GONE);
//                    }
//                }
//            }
//        }
//    }
}
