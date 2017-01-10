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

    public DayActivitiesModel(String day, long amActivityId, long pmActivityId) {
        this.day = day;
        this.amActivityId = amActivityId;
        this.pmActivityId = pmActivityId;
    }
}
