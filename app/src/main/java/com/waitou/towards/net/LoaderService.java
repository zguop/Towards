package com.waitou.towards.net;

import com.waitou.net_library.model.BaseResponse;
import com.waitou.net_library.model.RequestParams;
import com.waitou.towards.bean.BannerPageInfo;
import com.waitou.towards.bean.GankIoDayInfo;
import com.waitou.towards.bean.HomeFunctionInfo;
import com.waitou.towards.bean.JokeInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by waitou on 17/1/3.
 * api
 */

public interface LoaderService {


    @GET("master/beats_banner.json")
    Observable<BaseResponse<List<List<BannerPageInfo>>>> getBannerPage(); //首页轮播图


    @GET("master/wt_home.json")
    Observable<BaseResponse<HomeFunctionInfo>> getHomeData(); //首页


    @GET("randJoke.php")
    Observable<BaseResponse<List<JokeInfo>>> getTextJoke(@QueryMap RequestParams param); //笑话数据

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * eg:http://gank.io/api/day/2015/08/06
     */
    @GET("day/{year}/{month}/{day}")
    Observable<GankIoDayInfo> getGankIoDay(@Path("year") String year, @Path("month") String month, @Path("day") String day);


}
