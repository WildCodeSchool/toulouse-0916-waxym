package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * Model to send / get user saved activities
 */

public class DayActivitiesModel {

    @SerializedName(Constants.DAY_ACTIVITIES_MODEL_DAY)
    String day;

    @SerializedName(Constants.DAY_ACTIVITIES_MODEL_AM_ACTIVITY_ID)
    long amActivityId;

    @SerializedName(Constants.DAY_ACTIVITIES_MODEL_PM_ACTIVITY_ID)
    long pmActivityId;

    public DayActivitiesModel(String day, long amActivityId) {
        this.day = day;
        this.amActivityId = amActivityId;
    }

    public DayActivitiesModel(long pmActivityId, String day) {
        this.pmActivityId = pmActivityId;
        this.day = day;
    }

    public DayActivitiesModel(String day, long amActivityId, long pmActivityId) {
        this.day = day;
        this.amActivityId = amActivityId;
        this.pmActivityId = pmActivityId;
    }

    public String getDay() {
        return day;
    }

    public long getAmActivityId() {
        return amActivityId;
    }

    public long getPmActivityId() {
        return pmActivityId;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setAmActivityId(long amActivityId) {
        this.amActivityId = amActivityId;
    }

    public void setPmActivityId(long pmActivityId) {
        this.pmActivityId = pmActivityId;
    }
}
