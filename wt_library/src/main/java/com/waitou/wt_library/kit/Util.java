package com.waitou.wt_library.kit;

import java.util.List;

/**
 * Created by waitou on 17/3/24.
 */

public class Util {
    public static boolean isNotEmptyList(List<?> list) {
        return list != null && list.size() > 0;
    }
}
