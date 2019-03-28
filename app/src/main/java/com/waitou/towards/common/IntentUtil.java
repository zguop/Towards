package com.waitou.towards.common;

import android.content.Context;
import android.content.Intent;

import com.waitou.towards.model.movie.MovieRecommendActivity;
import com.waitou.wt_library.browser.WebUtil;

/**
 * Created by waitou on 17/5/25.
 */

public class IntentUtil {

    public static void go(Context context, String pathValue, String value) {
        //H5
        if (pathValue.equals(ExtraValue.PATH_H5)) {
            WebUtil.turnWeb(context, value, "");
        }
        //影视推荐
        else if (pathValue.equals(ExtraValue.PATH_MOVIE_RECOMMEND)) {
            Intent intent = new Intent(context, MovieRecommendActivity.class);
            context.startActivity(intent);
        }
    }
}
