package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * used to get list of possibles activities for user on server
 */

public class ListOfActivitiesModel {

    @SerializedName(Constants.LIST_OF_ACTIVITIES_MODEL)
    private ArrayList<ActivitiesModel> listOfActivities;

    public ListOfActivitiesModel(ArrayList<ActivitiesModel> listOfActivities) {
        this.listOfActivities = listOfActivities;
    }

    public ArrayList<ActivitiesModel> getListOfActivities() {
        return listOfActivities;
    }
}
