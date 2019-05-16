package com.app.boxee.shopper.data;

import android.os.Parcel;
import android.os.Parcelable;

public class SelectModel implements Parcelable{
    protected SelectModel(Parcel in) {
        status = in.readString();
        count = in.readString();
        mode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(count);
        dest.writeString(mode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SelectModel> CREATOR = new Creator<SelectModel>() {
        @Override
        public SelectModel createFromParcel(Parcel in) {
            return new SelectModel(in);
        }

        @Override
        public SelectModel[] newArray(int size) {
            return new SelectModel[size];
        }
    };

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    private String status;

    public SelectModel(String status, String count,String mode) {
        this.status = status;
        this.count = count;
        this.mode = mode;
    }

    private String count;
    private String mode;

}
