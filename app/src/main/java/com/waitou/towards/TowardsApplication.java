package com.waitou.towards;

import cn.droidlover.xdroid.base.BaseApplication;
import com.waitou.net_library.http.HttpUtil;
import com.waitou.towards.util.AlertToast;

import com.waitou.lib_theme.SharedPref;
import cn.droidlover.xdroid.imageloader.ILFactory;

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

    }
}
