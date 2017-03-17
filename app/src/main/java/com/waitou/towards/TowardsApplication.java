package com.waitou.towards;

import com.facebook.stetho.Stetho;
import com.waitou.net_library.http.HttpUtil;
import com.waitou.towards.util.AlertToast;
import com.waitou.wt_library.BaseApplication;
import com.waitou.wt_library.cache.SharedPref;
import com.waitou.wt_library.imageloader.ILFactory;


/**
 * Created by waitou on 17/1/3.
 */

public class TowardsApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化网络环境
        HttpUtil.init(this);
        //初始化sp工具
        SharedPref.init(this);
        //初始化吐司工具
        AlertToast.init(this);
        //glide加载初始化
        ILFactory.getLoader().init(this);
        //通过chrome来查看android数据库 chrome://inspect/#devices
        Stetho.initializeWithDefaults(this);
    }
}
