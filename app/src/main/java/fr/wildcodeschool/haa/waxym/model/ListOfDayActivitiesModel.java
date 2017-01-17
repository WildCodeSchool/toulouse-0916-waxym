package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tuffery on 17/01/17.
 */

public class ListOfDayActivitiesModel {
    @SerializedName("dayActivities")
    private ArrayList<DayActivitiesModel> dayActivitiesList;

    public ListOfDayActivitiesModel(ArrayList<DayActivitiesModel> dayActivitiesList) {
        this.dayActivitiesList = dayActivitiesList;
    }

    public ArrayList<DayActivitiesModel> getDayActivitiesList() {
        return dayActivitiesList;
    }
}
