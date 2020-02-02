package com.to.aboomy.recycler_lib.adapter;

/**
 * auth aboom
 * date 2018/7/22
 */
public interface Displayable {
    default Class[] getHolderClass() {
        return null;
    }
}
