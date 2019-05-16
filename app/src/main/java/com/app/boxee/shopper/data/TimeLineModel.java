package com.app.boxee.shopper.data;

import android.os.Parcel;
import android.os.Parcelable;


public class TimeLineModel implements Parcelable {

    private String mMessage;
    private String mDate;
    private OrderStatus mStatus;

    protected TimeLineModel(Parcel in) {
        mMessage = in.readString();
        mDate = in.readString();
        mcountry = in.readString();
        mcity = in.readString();
        muser = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMessage);
        dest.writeString(mDate);
        dest.writeString(mcountry);
        dest.writeString(mcity);
        dest.writeString(muser);
    }

    public static final Creator<TimeLineModel> CREATOR = new Creator<TimeLineModel>() {
        @Override
        public TimeLineModel createFromParcel(Parcel in) {
            return new TimeLineModel(in);
        }

        @Override
        public TimeLineModel[] newArray(int size) {
            return new TimeLineModel[size];
        }
    };

    public String getMcountry() {
        return mcountry;
    }

    public void setMcountry(String mcountry) {
        this.mcountry = mcountry;
    }

    public String getMcity() {
        return mcity;
    }

    public void setMcity(String mcity) {
        this.mcity = mcity;
    }

    public String getMuser() {
        return muser;
    }

    public void setMuser(String muser) {
        this.muser = muser;
    }

    private String mcountry;
    private String mcity;
    private String muser;




    public TimeLineModel() {
    }

    public TimeLineModel(String mMessage, String mDate, OrderStatus mStatus,String mcountry,String mcity, String muser) {
        this.mMessage = mMessage;
        this.mDate = mDate;
        this.mStatus = mStatus;
        this.mcity = mcity;
        this.mcountry = mcountry;
        this.muser = muser;
    }

    public String getMessage() {
        return mMessage;
    }

    public void semMessage(String message) {
        this.mMessage = message;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public OrderStatus getStatus() {
        return mStatus;
    }

    public void setStatus(OrderStatus mStatus) {
        this.mStatus = mStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
