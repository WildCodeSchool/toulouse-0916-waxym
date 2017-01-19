package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * used to send/get list of saved activities on server
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
