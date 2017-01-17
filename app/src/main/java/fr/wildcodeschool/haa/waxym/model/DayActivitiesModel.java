package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devauxarthur on 10/01/2017.
 */

public class DayActivitiesModel {

    @SerializedName("day")
    String day;

    @SerializedName("amActivityId")
    long amActivityId;

    @SerializedName("pmActivityId")
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
