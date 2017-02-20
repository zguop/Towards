package com.waitou.towards.bean;

import com.waitou.net_library.model.Displayable;

/**
 * Created by waitou on 17/2/20.
 */

public class CanInfo<T> implements Displayable {

    public T t;

    public CanInfo(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

}
