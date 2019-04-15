package com.waitou.towards.net;

import com.waitou.towards.bean.PatchInfo;
import com.waitou.towards.bean.BaseResponse;
import com.waitou.towards.bean.MovieBaseResponse;
import com.waitou.net_library.model.RequestParams;
import com.waitou.towards.bean.BannerPageInfo;
import com.waitou.towards.bean.FunctionInfo;
import com.waitou.towards.bean.GankIoDayInfo;
import com.waitou.towards.bean.GankResultsInfo;
import com.waitou.towards.bean.GankResultsTypeInfo;
import com.waitou.towards.bean.JokeInfo;
import com.waitou.towards.bean.MovieResInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by waitou on 17/1/3.
 * api
 */

public interface LoaderService {

    /*--------------- 存放在gitbub的json ---------------*/

    @GET("master/wt_logo.json")
    Observable<List<String>> getLogoList(); //logo图

    @GET("master/beats_banner.json")
    Observable<List<BannerPageInfo>> getBannerPage(); //首页轮播图

    @GET("master/wt_home.json")
    Observable<List<FunctionInfo>> getHomeData(); //首页

    @GET("master/wt_patch.json")
    Observable<PatchInfo> checkPatch();



    /*--------------- 段子接口 ---------------*/

    @GET("randJoke.php")
    Observable<BaseResponse<List<JokeInfo>>> getTextJoke(@QueryMap RequestParams param); //笑话数据



    /*--------------- gank 干货接口 ---------------*/

    //获取图片
    @GET("data/福利/10/{page}")
    Observable<GankIoDayInfo<List<GankResultsTypeInfo>>> getGirlPics(@Path("page") int page);

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * eg:http://gank.io/api/day/2015/08/06
     */
    @GET("day/{year}/{month}/{day}")
    Observable<GankIoDayInfo<GankResultsInfo>> getGankIoDay(@Path("year") String year, @Path("month") String month, @Path("day") String day);

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/10/{page}")
    Observable<GankIoDayInfo<List<GankResultsTypeInfo>>> getGankIoData(@Path("type") String type, @Path("page") int page);


    /*--------------- 影视 movie ---------------*/
    @GET("homePageApi/homePage.do")
    Observable<MovieBaseResponse<MovieResInfo>> getHomeMoviePage();


}
