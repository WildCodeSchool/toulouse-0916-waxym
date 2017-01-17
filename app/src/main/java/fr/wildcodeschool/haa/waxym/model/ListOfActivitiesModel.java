package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * Created by tuffery on 16/01/17.
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
