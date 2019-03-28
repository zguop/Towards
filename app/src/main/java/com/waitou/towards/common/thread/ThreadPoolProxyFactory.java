package com.waitou.towards.common.thread;

/**
 * Created by waitou on 17/3/27.
 * 线程池的代理
 */

public class ThreadPoolProxyFactory {

    private static ThreadPoolProxy mNormalThreadPoolProxy;

    /**
     * 普通线程代理
     */
    public static ThreadPoolProxy createNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolProxy(3, 3, 3000);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }
}
