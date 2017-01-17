package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * Created by devauxarthur on 10/01/2017.
 */

public class DayActivitiesModel {

    @SerializedName(Constants.DAY_ACTIVITIES_MODEL_DAY)
    String day;

    @SerializedName(Constants.DAY_ACTIVITIES_MODEL_AM_ACTIVITY_ID)
    long amActivityId;

    @SerializedName(Constants.DAY_ACTIVITIES_MODEL_PM_ACTIVITY_ID)
    long pmActivityId;

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
}
