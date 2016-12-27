package fr.wildcodeschool.haa.waxym.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tuffery on 23/11/16.
 */

public class DayStuffModel implements Parcelable{
    private Date date;
    private String activity;
    private int morning;
    private int afternoon;
    private String contractNumber;
    private String userName;
    private int userId;
    private String activityColor;

    public DayStuffModel(Date date, String activity, String contractNumber, String activityColor, int morning, int afternoon, String userName, int userId) {
        this.date = date;
        this.activity = activity;
        this.activityColor = activityColor;
        this.morning = morning;
        this.afternoon = afternoon;
        this.contractNumber = contractNumber;
        this.userName = userName;
        this.userId = userId;
    }

    public DayStuffModel() {
    }

    protected DayStuffModel(Parcel in) {
        activity = in.readString();
        morning = in.readInt();
        afternoon = in.readInt();
        contractNumber = in.readString();
        userName = in.readString();
        userId = in.readInt();
        activityColor = in.readString();
    }

    public static final Creator<DayStuffModel> CREATOR = new Creator<DayStuffModel>() {
        @Override
        public DayStuffModel createFromParcel(Parcel in) {
            return new DayStuffModel(in);
        }

        @Override
        public DayStuffModel[] newArray(int size) {
            return new DayStuffModel[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getMorning() {
        return morning;
    }

    public void setMorning(int morning) {
        this.morning = morning;
    }

    public int getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(int afternoon) {
        this.afternoon = afternoon;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActivityColor() {
        return activityColor;
    }

    public void setActivityColor(String activityColor) {
        this.activityColor = activityColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(activity);
        dest.writeInt(morning);
        dest.writeInt(afternoon);
        dest.writeString(contractNumber);
        dest.writeString(userName);
        dest.writeInt(userId);
        dest.writeString(activityColor);
    }
}
