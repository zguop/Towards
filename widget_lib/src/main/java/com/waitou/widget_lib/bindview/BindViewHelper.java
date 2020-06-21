package com.waitou.widget_lib.bindview;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * auth aboom by 2018/2/26.
 */

public class BindViewHelper<T> {

    private final List<ViewHolder> mCachesList = new ArrayList<>();
    private final List<T> listData = new ArrayList<>();

    private WeakReference<ViewGroup> mWeakReference;
    private OnBindData<T> onBindData;

    public BindViewHelper() {
    }

    public BindViewHelper(ViewGroup viewGroup) {
        mWeakReference = new WeakReference<>(viewGroup);
    }

    public void setWeakReference(ViewGroup viewGroup) {
        if (mWeakReference != null) {
            mWeakReference.clear();
        }
        this.mWeakReference = new WeakReference<>(viewGroup);
    }

    public boolean hasGroup() {
        return mWeakReference != null && mWeakReference.get() != null;
    }

    public void notifyDataSetChanged() {
        if (onBindData == null) {
            return;
        }
        ViewGroup viewGroup = mWeakReference.get();
        if (viewGroup == null) {
            return;
        }
        if (listData.size() > 0) {
            if (listData.size() < viewGroup.getChildCount()) {//数据源小于现有子View，删除后面多的
                viewGroup.removeViews(listData.size(), viewGroup.getChildCount() - listData.size());
                //删除View也清缓存
                while (mCachesList.size() > listData.size()) {
                    mCachesList.remove(mCachesList.size() - 1);
                }
            }
            for (int i = 0; i < listData.size(); i++) {
                ViewHolder holder;
                if (mCachesList.size() - 1 >= i) {//说明有缓存，不用inflate，否则inflate
                    holder = mCachesList.get(i);
                } else {
                    View inflate = View.inflate(viewGroup.getContext(), onBindData.onLayout(), null);
                    holder = new ViewHolder(inflate);
                    mCachesList.add(holder);//inflate 出来后 add进来缓存
                }
                onBindData.onBind(i, listData.size(), listData.get(i), holder);
                //如果View没有父控件 添加
                if (holder.getConvertView().getParent() == null) {
                    viewGroup.addView(holder.getConvertView());
                    onBindData.onViewSize(i, listData.size(), holder);
                }
            }
        } else {
            viewGroup.removeAllViews();
        }
    }

    public void setBindData(OnBindData<T> onBindData) {
        this.onBindData = onBindData;
    }

    public T getItem(@IntRange(from = 0) int position) {
        if (position < listData.size()) {
            return listData.get(position);
        }
        return null;
    }

    public void addData(@NonNull T data) {
        listData.add(data);
        notifyDataSetChanged();
    }

    public void replaceData(@NonNull Collection<T> data) {
        listData.clear();
        listData.addAll(data);
        notifyDataSetChanged();
    }
}
