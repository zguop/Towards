package com.waitou.net_library.http;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.waitou.net_library.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Created by waitou on 17/1/3.
 */

public class HttpUtil {

    private static boolean isTestHttp; //是否测试的路径
    private static boolean isDebug = true;//是否使用测试环境

    private static final String TEST_HTTP    = "http://";
    private static final String ACTIVE_HTTPS = "https://";

    //笑话大全api
    public static final String RANDOM_BASE_URL = "v.juhe.cn/joke/";
    //干货api
    public static final String GANK_BASE_URL   = "gank.io/api/";
    //首页轮播api
    public final static String GITHUB_API      = "raw.githubusercontent.com/zguop/Waitou_Json/";
    //影视api
    public static final String SVIPMOVIE_API   = "api.svipmovie.com/front/";

    public final static String PIXIV_DAILY_URL = "http://www.pixiv.net/ranking.php?mode=daily&format=json";//&p=1


    //    public static final String ROUTE_URL = "route.showapi.com/852-2";


    private final static String TEST_DOMAIN_URL   = TEST_HTTP;
    private final static String ACTIVE_DOMAIN_URL = ACTIVE_HTTPS;

    private static String currentAddress;

    /**
     * 设置当前环境的url
     */
    public static String setCurrentUrl(String address) {
        return currentAddress = isTestHttp() ? ACTIVE_HTTPS + address : ACTIVE_DOMAIN_URL + address;
    }

    /**
     * 获取当前的路径
     */
    public static String getCurrentAddress() {
        return TextUtils.isEmpty(currentAddress) ? setCurrentUrl(RANDOM_BASE_URL) : currentAddress;
    }


    /**
     * 初始化环境调用
     */
    public static void init(Context context) {
        if (!BuildConfig.DEBUG) {
            isTestHttp = false;
            isDebug = false;
        } else {
            String isDebugStr = (String) getAppInfo(context).get("is_debug");
            isTestHttp = isDebugStr.equals("true");
            isDebug = true;
        }
//        setCurrentUrl(RANDOM_BASE_URL);
    }


    public static boolean isTestHttp() {
        return isTestHttp;
    }

    public static boolean isDebug() {
        return isDebug;
    }


    /**
     * 获取app_info文件。
     */
    private static Properties getAppInfo(Context context) {
        AssetManager assetManager = context.getAssets();
        Properties properties = new Properties();
        try {
            InputStream inputStream = assetManager.open("app_info");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
