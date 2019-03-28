package com.waitou.towards.enums;

import com.waitou.towards.R;

/**
 * Created by waitou on 17/2/21.
 */

public enum GankEnum {
    Android(R.drawable.svg_ic_android, "Android"),
    福利(R.drawable.svg_ic_aixin, "福利"),
    瞎推荐(R.drawable.svg_ic_xia, "瞎推荐"),
    App(R.drawable.svg_ic_app, "App"),
    iOS(R.drawable.svg_ic_ios, "iOS"),
    拓展资源(R.drawable.svg_ic_tuozhan, "拓展资源"),
    前端(R.drawable.svg_ic_qian, "前端"),
    休息视频(R.drawable.svg_ic_movie, "休息视频");

    private int    type;
    private String typeStr;

    GankEnum(int type, String typeStr) {
        this.type = type;
        this.typeStr = typeStr;
    }

    public int getType() {
        return type;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public static int valueFrom(String value) {
        switch (value) {
            case "Android":
                return Android.type;
            case "福利":
                return 福利.type;
            case "瞎推荐":
                return 瞎推荐.type;
            case "App":
                return App.type;
            case "iOS":
                return iOS.type;
            case "拓展资源":
                return 拓展资源.type;
            case "前端":
                return 前端.type;
            case "休息视频":
                return 休息视频.type;
            default:
                return Android.type;
        }
    }
}
