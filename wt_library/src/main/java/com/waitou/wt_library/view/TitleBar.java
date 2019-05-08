package com.waitou.wt_library.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.waitou.wt_library.R;


/**
 * Created by waitou on 17/1/26.
 * bar
 */

public class TitleBar extends Toolbar {

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!(context instanceof Activity)) {
            throw new IllegalArgumentException("Context 建议使用 Activity类型的");
        }
        LayoutInflater.from(context).inflate(R.layout.toolbar_content, this);
    }

    /**
     * 初始化标题栏
     */
    public TextView initializeHeaderNo(String header) {
        TextView title = findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText(header);
        return title;
    }

    /**
     * 设置左侧侧图标
     *
     * @param resId    资源id
     * @param listener 点击事件
     * @return ImageView
     */
    public ImageView setLeftIcon(int resId, View.OnClickListener listener) {
        ImageView leftImg = findViewById(R.id.left_back);
        leftImg.setVisibility(View.VISIBLE);
        leftImg.setImageResource(resId);
        if (listener != null) {
            leftImg.setOnClickListener(listener);
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
        TextView textMenu = findViewById(R.id.left_txt);
        textMenu.setVisibility(View.VISIBLE);
        textMenu.setText(leftTxt);
        if (listener != null) {
            textMenu.setOnClickListener(listener);
        }
        return textMenu;
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
        View view = findViewById(R.id.left_back);
        view.setVisibility(View.VISIBLE);
        view.setOnClickListener(listener);
        TextView title = findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText(header);
        return title;
    }

    /**
     * 设置右侧图标
     *
     * @param resId    资源id
     * @param listener 点击事件
     * @return ImageView
     */
    public ImageView setRightIcon(int resId, View.OnClickListener listener) {
        ImageView rightImg = findViewById(R.id.icon_menu);
        rightImg.setVisibility(View.VISIBLE);
        rightImg.setImageResource(resId);
        if (listener != null) {
            View click = findViewById(R.id.right_click);
            click.setOnClickListener(listener);
        }
        return rightImg;
    }

    /**
     * 设置右侧文字
     *
     * @param rightTxt 文字描述
     * @param listener 点击事件
     * @return setRightText
     */
    public TextView setRightText(String rightTxt, View.OnClickListener listener) {
        TextView textMenu = findViewById(R.id.text_menu);
        textMenu.setVisibility(View.VISIBLE);
        textMenu.setText(rightTxt);
        if (listener != null) {
            View click = findViewById(R.id.right_click);
            click.setOnClickListener(listener);
        }
        return textMenu;
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
    public void customTitleView(View showView) {
        int showViewHashCode = showView.hashCode();
        boolean flag = false;
        ViewGroup barContent = findViewById(R.id.bar_content);
        int childCount = barContent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = barContent.getChildAt(i);
            int childHashCode = childAt.hashCode();
            if (showViewHashCode == childHashCode) {
                childAt.setVisibility(VISIBLE);
                flag = true;
            } else {
                childAt.setVisibility(GONE);
            }
        }
        if (!flag) {
            showView.setVisibility(VISIBLE);
            barContent.addView(showView);
        }
    }
}
