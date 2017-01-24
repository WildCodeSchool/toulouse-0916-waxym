package fr.wildcodeschool.haa.waxym.dataObject;

import com.google.gson.annotations.SerializedName;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * Model to send / get user saved activities
 */

public class DayActivitiesDataObject {

    @SerializedName(Constants.DAY_ACTIVITIES_MODEL_DAY)
    String day;

    @SerializedName(Constants.DAY_ACTIVITIES_MODEL_AM_ACTIVITY_ID)
    Long amActivityId;

    @SerializedName(Constants.DAY_ACTIVITIES_MODEL_PM_ACTIVITY_ID)
    Long pmActivityId;

    public DayActivitiesDataObject(String day, Long amActivityId) {
        this.day = day;
        this.amActivityId = amActivityId;
    }

    public DayActivitiesDataObject(Long pmActivityId, String day) {
        this.pmActivityId = pmActivityId;
        this.day = day;
    }

    public DayActivitiesDataObject(String day, Long amActivityId, Long pmActivityId) {
        this.day = day;
        this.amActivityId = amActivityId;
        this.pmActivityId = pmActivityId;
    }

    public String getDay() {
        return day;
    }

    public Long getAmActivityId() {
        return amActivityId;
    }

    public Long getPmActivityId() {
        return pmActivityId;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void clearAmActivity(){
        this.amActivityId = null;
    }
    public void clearPmActivity(){
        this.pmActivityId = null;
    }

    public void setAmActivityId(long amActivityId) {
        this.amActivityId = amActivityId;
    }

    public void setPmActivityId(long pmActivityId) {
        this.pmActivityId = pmActivityId;
    }
}
