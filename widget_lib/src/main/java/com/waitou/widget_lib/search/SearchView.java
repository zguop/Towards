package com.waitou.widget_lib.search;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.waitou.widget_lib.R;
import com.waitou.widget_lib.shape.Shape;


/**
 * auth aboom
 * date 2018/8/23
 */
public class SearchView extends LinearLayout {

    private OnSearchListener mSearchListener;
    private OnFocusChangeListener onFocusChangeListener;
    private DrawableCenterEditText mEdit;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.widget_search, this);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        ImageView clear = findViewById(R.id.clear);
        mEdit = findViewById(R.id.edit);
        RelativeLayout mRlSearch = findViewById(R.id.rl_search);

        mRlSearch.setBackground(Shape.getShapeRectangle()
                .setRadius(SizeUtils.dp2px(15))
                .setSolid(Color.WHITE)
                .create());

        mEdit.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                if (mEdit.hasFocus() && !TextUtils.isEmpty(s.toString())) {
                    clear.setVisibility(View.VISIBLE);
                } else {
                    clear.setVisibility(View.GONE);
                }
            }
        });
        mEdit.setOnFocusChangeListener((v1, hasFocus) -> {
            if (onFocusChangeListener != null) {
                onFocusChangeListener.onFocusChange(v1, hasFocus);
            }
            if (hasFocus && !TextUtils.isEmpty(mEdit.getText())) {
                clear.setVisibility(View.VISIBLE);
            } else {
                clear.setVisibility(View.GONE);
            }
        });
        clear.setOnClickListener(v -> {
            mEdit.setText("");
            mEdit.requestFocus();
            KeyboardUtils.showSoftInput(mEdit);
        });

        mEdit.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                if (mSearchListener != null) {
                    mSearchListener.search(mEdit.getText().toString().trim());
                }
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        });

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.widget_SearchView);
        mEdit.setHint(a.getString(R.styleable.widget_SearchView_widget_search_hint));
        mEdit.setCenter(a.getBoolean(R.styleable.widget_SearchView_widget_search_default_center, true));
        a.recycle();
    }

    public interface OnSearchListener {
        void search(String search);
    }

    public void setSearchListener(OnSearchListener searchListener) {
        this.mSearchListener = searchListener;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    public void setSearchHint(String hint) {
        mEdit.setHint(hint);
    }

    public void setSearchHintColor(int color) {
        mEdit.setHintTextColor(color);
    }

    public void setSearchText(String text) {
        mEdit.setText(text);
        mEdit.setSelection(mEdit.length());
    }

    public void clearSearch() {
        if (!TextUtils.isEmpty(mEdit.getText())) {
            mEdit.setText("");
        }
        if (mEdit.hasFocus()) {
            mEdit.clearFocus();
        }
    }

    public EditText getEditView() {
        return mEdit;
    }

    /**
     * 输入类型
     */
    public void setInputType(int inputType) {
        int type;
        switch (inputType) {
            case 1://数字
                type = InputType.TYPE_CLASS_NUMBER;
                break;
            case 2://带小数点的数字
                type = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;
                break;
            case 3://文字密码
                type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                break;
            case 4://纯英文
                type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI;
                break;
            case 5://电话号码
                type = InputType.TYPE_CLASS_PHONE;
                break;
            case 6://密码显示
                type = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                break;
            default:
                type = InputType.TYPE_CLASS_TEXT;
        }
        mEdit.setInputType(type);
    }

    /**
     * 设置自定义的输入条件,包括系统自带的{@link InputFilter.LengthFilter}长度限制,输入字符限制
     */
    public void setFilter(InputFilter[] filters) {
        if (filters == null) {
            filters = mEdit.getFilters();
        }
        mEdit.setFilters(filters);
    }
}
