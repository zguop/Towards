package com.waitou.towards.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by waitou on 17/2/16.
 */

public class BannerPageInfo {

    @SerializedName("version")
    @Expose
    public Long version;

    @SerializedName("data")
    @Expose
    public List<BannerInfo> data;


    public static class BannerInfo {

        @SerializedName("id") //2881
        @Expose
        public Long id;

        @SerializedName("name") //海报（阿里星球
        @Expose
        public String name;

        @SerializedName("desc") //海报
        @Expose
        public String desc;

        @SerializedName("style") //8
        @Expose
        public int style;

        @SerializedName("isNameDisplay") //0
        @Expose
        public int isNameDisplay;

        @SerializedName("data")
        @Expose
        public List<BannerDataInfo> data;

        public static class BannerDataInfo {

            @SerializedName("id")
            @Expose
            public int id;

            @SerializedName("name")
            @Expose
            public String name;

            @SerializedName("desc")
            @Expose
            public String desc;

            @SerializedName("picUrl")
            @Expose
            public String picUrl;

        }

        public static class BannerAcionInfo {
            @SerializedName("type")
            @Expose
            public int type;

            @SerializedName("value")
            @Expose
            public String value;
        }

    }
}
