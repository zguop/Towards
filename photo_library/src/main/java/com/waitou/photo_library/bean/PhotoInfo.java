package com.waitou.photo_library.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by waitou on 17/4/3.
 * 图片信息
 */

public class PhotoInfo implements Parcelable {
    public String photoName;       //图片的名字
    public String photoPath;       //图片的路径
    public long   photoSize;         //图片的大小
    public int    photoWidth;         //图片的宽度
    public int    photoHeight;        //图片的高度
    public String photoMimeType;   //图片的类型
    public long   photoAddTime;      //图片的创建时间

    /**
     * 图片的路径和创建时间相同就认为是同一张图片
     */
    @Override
    public boolean equals(Object o) {
        try {
            PhotoInfo other = (PhotoInfo) o;
            return this.photoPath.equalsIgnoreCase(other.photoPath) && this.photoAddTime == other.photoAddTime;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "PhotoInfo{" +
                "photoName='" + photoName + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", photoSize=" + photoSize +
                ", photoWidth=" + photoWidth +
                ", photoHeight=" + photoHeight +
                ", photoMimeType='" + photoMimeType + '\'' +
                ", photoAddTime=" + photoAddTime +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.photoName);
        dest.writeString(this.photoPath);
        dest.writeLong(this.photoSize);
        dest.writeInt(this.photoWidth);
        dest.writeInt(this.photoHeight);
        dest.writeString(this.photoMimeType);
        dest.writeLong(this.photoAddTime);
    }

    public PhotoInfo() {
    }

    protected PhotoInfo(Parcel in) {
        this.photoName = in.readString();
        this.photoPath = in.readString();
        this.photoSize = in.readLong();
        this.photoWidth = in.readInt();
        this.photoHeight = in.readInt();
        this.photoMimeType = in.readString();
        this.photoAddTime = in.readLong();
    }

    public static final Creator<PhotoInfo> CREATOR = new Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel source) {
            return new PhotoInfo(source);
        }

        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };
}
