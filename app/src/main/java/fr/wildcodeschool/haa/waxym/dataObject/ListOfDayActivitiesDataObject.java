package fr.wildcodeschool.haa.waxym.dataObject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * used to send/get list of saved activities on server
 */

public class ListOfDayActivitiesDataObject {
    @SerializedName("dayActivities")
    private ArrayList<DayActivitiesDataObject> dayActivitiesList;

    public ListOfDayActivitiesDataObject(ArrayList<DayActivitiesDataObject> dayActivitiesList) {
        this.dayActivitiesList = dayActivitiesList;
    }

    public ArrayList<DayActivitiesDataObject> getDayActivitiesList() {
        return dayActivitiesList;
    }
}
