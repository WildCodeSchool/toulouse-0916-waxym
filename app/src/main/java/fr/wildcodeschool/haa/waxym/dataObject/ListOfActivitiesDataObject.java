package fr.wildcodeschool.haa.waxym.dataObject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * used to get list of possibles activities for user on server
 */

public class ListOfActivitiesDataObject {

    @SerializedName(Constants.LIST_OF_ACTIVITIES_MODEL)
    private ArrayList<ActivitiesDataObject> listOfActivities;

    public ListOfActivitiesDataObject(ArrayList<ActivitiesDataObject> listOfActivities) {
        this.listOfActivities = listOfActivities;
    }

    public ArrayList<ActivitiesDataObject> getListOfActivities() {
        return listOfActivities;
    }
}
