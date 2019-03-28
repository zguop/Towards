package com.waitou.wt_library.kit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.waitou.wt_library.BaseApplication;

/**
 * Created by waitou on 17/3/24.
 * 网络工具
 */

public class UNetWork {
    private static final String NETWORK_TYPE_WIFI       = "wifi";
    private static final String NETWORK_TYPE_3G         = "eg";
    private static final String NETWORK_TYPE_2G         = "2g";
    private static final String NETWORK_TYPE_WAP        = "wap";
    private static final String NETWORK_TYPE_UNKNOWN    = "unknown";
    private static final String NETWORK_TYPE_DISCONNECT = "disconnect";

    /**
     * 判断网络是否连接
     */
    public static boolean isAvailable() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    /**
     * 获取网络信息 需要权限 ACCESS_NETWORK_STATE
     */
    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static int getNetworkType() {
        NetworkInfo networkInfo = getActiveNetworkInfo();
        return networkInfo == null ? -1 : networkInfo.getType();
    }

    public static String getNetworkTypeName() {
        NetworkInfo networkInfo = getActiveNetworkInfo();
        String type = NETWORK_TYPE_DISCONNECT;
        if (networkInfo == null) {
            return type;
        }
        if (networkInfo.isConnected()) {
            String typeName = networkInfo.getTypeName();
            if ("WIFI".equalsIgnoreCase(typeName)) {
                type = NETWORK_TYPE_WIFI;
            } else if ("MOBILE".equalsIgnoreCase(typeName)) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork() ? NETWORK_TYPE_3G : NETWORK_TYPE_2G)
                        : NETWORK_TYPE_WAP;
            } else {
                type = NETWORK_TYPE_UNKNOWN;
            }
        }
        return type;
    }

    private static boolean isFastMobileNetwork() {
        TelephonyManager telephonyManager = (TelephonyManager) BaseApplication.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return false;
        }
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true;
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }
}
