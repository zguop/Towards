package com.waitou.photo_library.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by waitou on 17/4/3.
 */

public class PhotoFolderInfo implements Serializable {

    public String               folderName;  //当前文件夹的名字
    public String               folderPath;  //当前文件夹的路径
    public PhotoInfo            cover;   //当前文件夹需要要显示的缩略图，默认为最近的一次图片
    public ArrayList<PhotoInfo> photoList;  //当前文件夹下所有图片的集合

    /**
     * 只要文件夹的路径和名字相同，就认为是相同的文件夹
     */
    @Override
    public boolean equals(Object o) {
        try {
            PhotoFolderInfo other = (PhotoFolderInfo) o;
            return this.folderPath.equalsIgnoreCase(other.folderPath) && this.folderName.equalsIgnoreCase(other.folderName);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
