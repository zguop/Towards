package com.waitou.meta_provider_lib;

import android.app.Application;
import android.content.res.Configuration;

/**
 * auth aboom
 * date 2019/4/14
 */
public interface ISubApplication {

    /**
     * application创建
     */
    void onMainCreate(Application application);

    /**
     * 主进程外的进程会调用
     */
    void onOtherProcess(Application application, String process);

    void onBaseContextAttached();

    /**
     * application终止
     */
    void onTerminate();

    /**
     * application低内存
     */
    void onLowMemory();

    /**
     * application清理内存
     */
    void onTrimMemory(int level);


    void onConfigurationChange(Configuration newConfig);


}
