package com.waitou.wt_library.recycler;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.waitou.wt_library.R;


/**
 * Created by wanglei on 2016/10/30.
 */

public class SimpleLoadMoreFooter extends RelativeLayout implements LoadMoreUIHandler {

    private TextView    tvMsg;
    private ProgressBar progressBar;

    public SimpleLoadMoreFooter(Context context) {
        this(context, null);
    }

    public SimpleLoadMoreFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleLoadMoreFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    private void setupView() {
        inflate(getContext(), R.layout.view_x_footer_load_more_simple, this);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onLoading() {
        setVisibility(VISIBLE);
        tvMsg.setText("加载中");
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onLoadFinish(boolean hasMore) {
        if (hasMore) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
            tvMsg.setText("没有更多数据");
            progressBar.setVisibility(GONE);
        }
    }

    @Override
    public void onErrorMore(String des) {
        setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        tvMsg.setText(TextUtils.isEmpty(des) ? "网络异常，请更换网络或稍后再试" : des);
    }
}

