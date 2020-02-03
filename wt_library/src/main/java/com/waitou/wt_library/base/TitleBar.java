package com.waitou.wt_library.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.waitou.wt_library.R;


/**
 * Created by waitou on 17/1/26.
 * bar
 */

public class TitleBar extends FrameLayout {

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!(context instanceof Activity)) {
            throw new IllegalArgumentException("Context 建议使用 Activity类型的");
        }
        LayoutInflater.from(context).inflate(R.layout.toolbar_content, this, true);
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        setElevation((int) (5 * scale + 0.5f));
        setBackgroundColor(Color.WHITE);
    }

    /**
     * 初始化标题栏
     */
    public TextView initializeHeaderNo(String header) {
        visible();
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
    public ImageView setLeftIcon(int resId, OnClickListener listener) {
        visible();
        ImageView leftImg = findViewById(R.id.left_back);
        leftImg.setVisibility(View.VISIBLE);
        leftImg.setImageResource(resId);
        if (listener != null) {
            ((View) leftImg.getParent()).setOnClickListener(v -> listener.onClick(leftImg));
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
    public TextView setLeftText(String leftTxt, OnClickListener listener) {
        visible();
        TextView textMenu = findViewById(R.id.left_txt);
        textMenu.setVisibility(View.VISIBLE);
        textMenu.setText(leftTxt);
        if (listener != null) {
            ((View) textMenu.getParent()).setOnClickListener(v -> listener.onClick(textMenu));
        }
        return textMenu;
    }

    /**
     * 初始化标题栏
     *
     * @param header 标题文字
     */
    public TextView initializeHeader(String header) {
        visible();
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
    public TextView initializeHeader(String header, OnClickListener listener) {
        visible();
        View view = findViewById(R.id.left_back);
        view.setVisibility(View.VISIBLE);
        if (listener != null) {
            ((View) view.getParent()).setOnClickListener(v -> listener.onClick(view));
        }
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
    public ImageView setRightIcon(int resId, OnClickListener listener) {
        visible();
        ImageView rightImg = findViewById(R.id.icon_menu);
        rightImg.setVisibility(View.VISIBLE);
        rightImg.setImageResource(resId);
        if (listener != null) {
            ((View) rightImg.getParent()).setOnClickListener(v -> listener.onClick(rightImg));
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
    public TextView setRightText(String rightTxt, OnClickListener listener) {
        visible();
        TextView textMenu = findViewById(R.id.text_menu);
        textMenu.setVisibility(View.VISIBLE);
        textMenu.setText(rightTxt);
        if (listener != null) {
            ((View) textMenu.getParent()).setOnClickListener(v -> listener.onClick(textMenu));
        }
        return textMenu;
    }

    public void restoreTitleView(String title) {
        replaceTitleView(null);
        initializeHeaderNo(title);
    }

    /**
     * 自定义标题栏view
     */
    public void replaceTitleView(View showView) {
        visible();
        ViewGroup barContent = findViewById(R.id.bar_content);
        int childCount = barContent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = barContent.getChildAt(i);
            if (childAt.getId() == R.id.title) {
                childAt.setVisibility(showView == null ? VISIBLE : GONE);
            } else {
                barContent.removeView(childAt);
            }
        }
        if (showView != null) {
            barContent.addView(showView);
        }
    }

    private void visible() {
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
    }
}
