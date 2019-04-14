package com.waitou.towards.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * author   itxp
 * date     2018/5/12 19:36
 * des
 */

public class PatchInfo {

    @SerializedName("downloadUrl")
    @Expose
    public String downloadUrl; //不为空则表明有更新

    @SerializedName("versionName")
    @Expose
    public String versionName; //本次patch包的版本号

    @SerializedName("patchMessage")
    @Expose
    public String patchMessage; //本次patch包含的相关信息，例如：主要做了那些改动

    @SerializedName("patchVersion")
    @Expose
    public int patchVersion;

    @SerializedName("md5")
    @Expose
    public String md5;//patch文件正确的md5值

}
