package com.waitou.towards.net;

import com.waitou.net_library.DataServiceProvider;

/**
 * Created by waitou on 17/1/3.
 */

public class DataLoader {
    public static LoaderService getInstance() {
        return DataServiceProvider.getInstance().provide(LoaderService.class);
    }
}
