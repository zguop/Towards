package com.waitou.widget_lib.bindview;

import android.util.SparseArray;
import android.view.View;

/**
 * auth aboom by 2018/7/26.
 */

public class ViewHolder {

    private View              mConvertView;
    private SparseArray<View> mViews;

    public ViewHolder(View view) {
        this.mConvertView = view;
        this.mViews = new SparseArray<>();
    }

    /**
     * 通过viewId获取控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }
}
