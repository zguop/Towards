package com.waitou.normal_dialog_lib;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class IDialogView implements Parcelable {

    protected abstract View getContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public IDialogView() {
    }

    protected IDialogView(Parcel in) {
    }

    public static final Creator<IDialogView> CREATOR = new Creator<IDialogView>() {
        @Override
        public IDialogView createFromParcel(Parcel source) {
            return new IDialogView(source){
                @Override
                protected View getContentView(@NonNull LayoutInflater inflater, ViewGroup container) {
                    return null;
                }
            };
        }

        @Override
        public IDialogView[] newArray(int size) {
            return new IDialogView[size];
        }
    };
}
