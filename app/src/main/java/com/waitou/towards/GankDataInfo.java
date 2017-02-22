package com.waitou.towards;

import com.waitou.net_library.model.Displayable;
import com.waitou.towards.bean.GankResultsTypeInfo;

import java.util.List;

/**
 * Created by waitou on 17/2/21.
 */

public class GankDataInfo implements Displayable {

    public String title;
    public int type = -1;
    public List<GankResultsTypeInfo> gankResultsTypeInfos;
}
