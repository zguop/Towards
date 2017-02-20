package com.waitou.towards.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by waitou on 17/2/17.
 */

public class HomeFunctionInfo {

    @SerializedName("function")
    @Expose
    public List<FunctionInfo> function;


    public static class FunctionInfo {

        @SerializedName("picUrl")
        @Expose
        public String picUrl;

        @SerializedName("typeName")
        @Expose
        public String typeName;

        @SerializedName("type")
        @Expose
        public int type;

        @SerializedName("link")
        @Expose
        public String link;
    }
}
