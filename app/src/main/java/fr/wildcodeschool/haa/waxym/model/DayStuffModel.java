package fr.wildcodeschool.haa.waxym.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * Model to get/save all informations on a saved day activity
 */

public class DayStuffModel implements Parcelable {
    private Date date;
    private String activity;
    private int morning;
    private int afternoon;
    private long contractNumber;
    private String userName;
    private long userId;
    private String activityColor;
    private int sendState;
    private Long activityId;
    private long activityDetailType;

    public DayStuffModel(Date date, String activity, long contractNumber, String activityColor, int morning, int afternoon, String userName, int userId, int sendState, long activityId, long activityDetailType) {
        this.date = date;
        this.activity = activity;
        this.activityColor = activityColor;
        this.morning = morning;
        this.afternoon = afternoon;
        this.contractNumber = contractNumber;
        this.userName = userName;
        this.userId = userId;
        this.sendState = sendState;
        this.activityId = activityId;
        this.activityDetailType = activityDetailType;
    }

    public DayStuffModel() {
    }


    protected DayStuffModel(Parcel in) {
        activity = in.readString();
        morning = in.readInt();
        afternoon = in.readInt();
        contractNumber = in.readLong();
        userName = in.readString();
        userId = in.readLong();
        activityColor = in.readString();
        sendState = in.readInt();
        activityId = in.readLong();
        activityDetailType = in.readLong();
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    public long getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(long contractNumber) {
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

    public int getSendState() {
        return sendState;
    }

    public void setSendState(int sendState) {
        this.sendState = sendState;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public long getActivityDetailType() {
        return activityDetailType;
    }

    public void setActivityDetailType(long activityDetailType) {
        this.activityDetailType = activityDetailType;
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
        dest.writeLong(contractNumber);
        dest.writeString(userName);
        dest.writeLong(userId);
        dest.writeString(activityColor);
        dest.writeInt(sendState);
        dest.writeLong(activityId);
        dest.writeLong(activityDetailType);
    }

    public int determineActyvityTypeID() {
        if (getMorning() == getAfternoon()) {
            return Constants.ID_ERROR;
        } else {
            if (getMorning() == 1)
                return Constants.MORNING_ID;
            else
                return Constants.AFTERNOON_ID;
        }
    }
}
