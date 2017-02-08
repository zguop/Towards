package com.waitou.towards.net;

import com.waitou.net_library.model.BaseResponse;
import com.waitou.net_library.model.RequestParams;
import com.waitou.towards.bean.JokeInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by waitou on 17/1/3.
 * api
 */

public interface LoaderService {

    @GET("randJoke.php")
    Observable<BaseResponse<List<JokeInfo>>> getTextJoke(@QueryMap RequestParams param);


}
