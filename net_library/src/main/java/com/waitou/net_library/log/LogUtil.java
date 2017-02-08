package com.waitou.net_library.log;

import android.text.TextUtils;
import android.util.Log;

import com.waitou.net_library.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by waitou on 17/1/3.
 * 日志打印工具
 */

public class LogUtil {

    private static String DEFAULT_TAG = "towards";

    private static final char   TOP_LEFT_CORNER        = '╔';
    private static final char   BOTTOM_LEFT_CORNER     = '╚';
    private static final char   MIDDLE_CORNER          = '╟';
    private static final char   HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER         = "═════════════════════════════════════";
    private static final String SINGLE_DIVIDER         = "─────────────────────────────────────";
    private static final String TOP_BORDER             = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER          = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER          = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;
    private static final String SEP                    = System.getProperty("line.separator");

    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            largeLogV(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            largeLogI(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            largeLogE(tag, msg);
        }
    }


    public static void v(String msg) {
        if (BuildConfig.DEBUG) {
            largeLogV(DEFAULT_TAG, msg);
        }
    }

    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            largeLogI(DEFAULT_TAG, msg);
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            largeLogE(DEFAULT_TAG, msg);
        }
    }

    private static void largeLogI(String tag, String content) {
        if (!tag.contains(DEFAULT_TAG)) {
            tag = DEFAULT_TAG + "-" + tag;
        }
        if (!TextUtils.isEmpty(content)) {
            if (content.length() > 3000) {
                Log.i(tag, content.substring(0, 3000));
                largeLogI(tag, content.substring(3000));
            } else {
                Log.i(tag, content);
            }
        }
    }

    private static void largeLogV(String tag, String content) {
        if (!tag.contains(DEFAULT_TAG)) {
            tag = DEFAULT_TAG + "-" + tag;
        }
        if (!TextUtils.isEmpty(content)) {
            if (content.length() > 3000) {
                Log.v(tag, content.substring(0, 3000));
                largeLogV(tag, content.substring(3000));
            } else {
                Log.v(tag, content);
            }
        }
    }

    private static void largeLogE(String tag, String content) {
        if (!tag.contains(DEFAULT_TAG)) {
            tag = DEFAULT_TAG + "-" + tag;
        }
        if (!TextUtils.isEmpty(content)) {
            if (content.length() > 3000) {
                int i = content.substring(0, 3000).indexOf(HORIZONTAL_DOUBLE_LINE, 2940);
                if (i < 0) {
                    Log.e(tag, content.substring(0, 3000));
                    largeLogE(tag, content.substring(3000));
                } else {
                    Log.e(tag, content.substring(0, i));
                    largeLogE(tag, content.substring(i));
                }
            } else {
                Log.e(tag, content);
            }
        }
    }

    public static void post(String tag, String content) {
        if (!BuildConfig.DEBUG) return;
        logHeader(tag, content);
    }

    public static void json(String tag, String json) {
        if (!BuildConfig.DEBUG) return;
        int indent = 4;
        if (TextUtils.isEmpty(json)) {
            logJsonLine(tag, "JSON{json is empty}");
            return;
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String msg = jsonObject.toString(indent);
                logJsonLine(tag, msg);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String msg = jsonArray.toString(indent);
                logJsonLine(tag, msg);
            }
        } catch (JSONException e) {
            logJsonLine(tag, e.toString() + "\njson = " + json);
        } catch (OutOfMemoryError error) {
            e(tag, error.toString() + "\n\njson = " + json);
        }
        largeLogI(tag, json);
    }

    private static void logHeader(String tag, String content) {
        content = TOP_BORDER + SEP + HORIZONTAL_DOUBLE_LINE + " " + content;
        if (content.contains(".png") || content.contains(".jpg") || content.contains(".jpeg")) {
            content = content + SEP + BOTTOM_BORDER;
        }
        largeLogE(tag, content);
    }

    private static void logJsonLine(String tag, String content) {
        content = content.replaceAll(SEP, SEP + HORIZONTAL_DOUBLE_LINE + " ");
        largeLogE(tag, MIDDLE_BORDER);
        largeLogE(tag, HORIZONTAL_DOUBLE_LINE + " " + content);
        largeLogE(tag, BOTTOM_BORDER);
    }
}
