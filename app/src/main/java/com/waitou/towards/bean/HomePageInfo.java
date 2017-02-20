package com.waitou.towards.bean;

import com.waitou.net_library.model.BaseResponse;

import java.util.List;

/**
 * Created by waitou on 17/2/18.
 */

public class HomePageInfo {

    public BaseResponse<List<List<BannerPageInfo>>> listBaseResponse;

    public BaseResponse<HomeFunctionInfo> homeFunctionInfoBaseResponse;

    public GankIoDayInfo gankIoDayInfo;

    public HomePageInfo(BaseResponse<List<List<BannerPageInfo>>> listBaseResponse, BaseResponse<HomeFunctionInfo> homeFunctionInfoBaseResponse, GankIoDayInfo gankIoDayInfo) {
        this.listBaseResponse = listBaseResponse;
        this.homeFunctionInfoBaseResponse = homeFunctionInfoBaseResponse;
        this.gankIoDayInfo = gankIoDayInfo;
    }
}
