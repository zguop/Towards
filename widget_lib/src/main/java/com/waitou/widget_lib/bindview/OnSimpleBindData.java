package com.waitou.widget_lib.bindview;

/**
 * auth aboom by 2018/7/26.
 */

public abstract class OnSimpleBindData<T> implements OnBindData<T> {

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    protected void itemClick(int position) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onClick(position);
        }
    }

    @Override
    public void onViewSize(int pos, int itemCount, ViewHolder holder) {
    }
}
