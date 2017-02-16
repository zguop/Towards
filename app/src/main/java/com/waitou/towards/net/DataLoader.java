package com.waitou.towards.net;

import com.waitou.net_library.DataServiceProvider;
import com.waitou.net_library.http.HttpUtil;

/**
 * Created by waitou on 17/1/3.
 * 请求控制类
 */

public class DataLoader {

    public static LoaderService getJokeApi() {
        return DataServiceProvider.getInstance().provide(HttpUtil.RANDOM_BASE_URL, LoaderService.class);
    }

    public static LoaderService getBannerApi() {
        return DataServiceProvider.getInstance().provide(HttpUtil.API_DONG_TING, LoaderService.class);
    }
}
