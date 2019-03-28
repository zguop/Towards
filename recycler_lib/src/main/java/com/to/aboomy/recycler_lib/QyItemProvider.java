package com.to.aboomy.recycler_lib;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * auth aboom
 * date 2018/7/22
 * 做属性扩展
 */
public abstract class QyItemProvider<T, V extends BaseViewHolder> extends BaseItemProvider<T, V> {

    public abstract Object object();
}
