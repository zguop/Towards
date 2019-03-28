package com.waitou.towards.common.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by waitou on 17/3/27.
 * 线程池的代码,对现场操作的一些封装,只需要暴露用户真正关心的操作
 */

class ThreadPoolProxy {
    private ThreadPoolExecutor mExecutor;
    private int                mCorePoolSize;
    private int                mMaximumPoolSize;
    private long               mKeepAliveTime;

    ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
        mKeepAliveTime = keepAliveTime;
    }

    //初始化
    private void initThreadPoolExecutor() {
        //如果等于 null  或者已经被关闭了 或者任务执行完成立马要关闭了
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
            //单利的模式 初始化
            synchronized (ThreadPoolProxy.class) {
                //外部决定通过构造函数初始化
                int corePoolSize = mCorePoolSize;
                int maximumPoolSize = mMaximumPoolSize;
                long keepAliveTime = mKeepAliveTime;

                TimeUnit unit = TimeUnit.MILLISECONDS; // 单位毫秒
                BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();  // 队列
                ThreadFactory threadFactory = Executors.defaultThreadFactory(); //工厂
                RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy(); //处理方式:不做处理
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
                    //初始化
                    mExecutor = new ThreadPoolExecutor(corePoolSize, //核心线程数
                            maximumPoolSize, //最大线程池数
                            keepAliveTime, //保持时间
                            unit, //保持时间单位
                            workQueue, //任务队列
                            threadFactory,//线程工厂
                            handler); //异常捕获器
                }
            }
        }
    }

    /**
     * 1.提交任务和执行任务的区别
     *      是否有返回值,提交任务有返回值
     * 2.Future<?>是什么?
     *      1.得到任务执行之后的结果
     *      2.包含了一个get方法和cancel
     *      3.其中get方法,是一个阻塞的方法,会阻塞等待任务执行完成之后的结果,还可以try catch到任务执行过程中抛出的异常
     */


    /**
     * 提交任务
     */
    public Future<?> submit(Runnable task) {
        initThreadPoolExecutor();
        return mExecutor.submit(task);
    }

    /**
     * 执行任务
     */
    public void execute(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }

    /**
     * 移除任务
     */
    public void remove(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }

}
