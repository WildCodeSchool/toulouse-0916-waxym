package fr.wildcodeschool.haa.waxym.model;

import java.util.Date;

/**
 * Created by tuffery on 23/11/16.
 */

public class DayStuffModel {
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
}
