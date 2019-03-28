package com.to.aboomy.rx_lib;

/**
 * Created by waitou on 17/1/11.
 */

public class RxBusBaseEvent<T> {

    private int code;
    private T object;

    public RxBusBaseEvent(int code,T o){
        this.code = code;
        this.object = o;
    }

    public int getCode() {
        return code;
    }

    public T getObject() {
        return object;
    }
}
