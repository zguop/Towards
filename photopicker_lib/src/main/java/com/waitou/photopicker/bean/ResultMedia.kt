package com.waitou.photopicker.bean

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

/**
 * auth aboom
 * date 2019-06-01
 */
class ResultMedia() : Parcelable {

    var mediaPath: String? = null

    var mediaUri: Uri? = null


    constructor(mediaPath: String, mediaUri: Uri) : this() {
        this.mediaPath = mediaPath
        this.mediaUri = mediaUri
    }

    constructor(parcel: Parcel) : this() {
        mediaPath = parcel.readString()
        mediaUri = parcel.readParcelable(Uri::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mediaPath)
        parcel.writeParcelable(mediaUri, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultMedia> {
        override fun createFromParcel(parcel: Parcel): ResultMedia {
            return ResultMedia(parcel)
        }

        override fun newArray(size: Int): Array<ResultMedia?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "ResultMedia(mediaPath=$mediaPath, mediaUri=$mediaUri)"
    }
}
