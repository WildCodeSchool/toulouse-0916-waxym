package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tuffery on 16/01/17.
 */

public class ListOfActivitiesModel {

    @SerializedName("activities")
    private ArrayList<ActivitiesModel> listOfActivities;

    public ListOfActivitiesModel(ArrayList<ActivitiesModel> listOfActivities) {
        this.listOfActivities = listOfActivities;
    }

    public ArrayList<ActivitiesModel> getListOfActivities() {
        return listOfActivities;
    }
}
