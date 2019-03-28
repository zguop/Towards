package com.waitou.three_library.share;

/**
 * Created by waitou on 17/5/16.
 */

public class ShareInfo {

    public static final int TEXT           = 1; //纯文本
    public static final int IMAGE          = 2;//图片
    public static final int TEXT_AND_IMAGE = 3;//图片和文本
    public static final int WEB0           = 4; //链接


    public String title;

    public String content;

    public String imageUrl;

    public String targetUrl;

    public int type;

    @Override
    public String toString() {
        return "ShareInfo{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", targetUrl='" + targetUrl + '\'' +
                ", type=" + type +
                '}';
    }
}
