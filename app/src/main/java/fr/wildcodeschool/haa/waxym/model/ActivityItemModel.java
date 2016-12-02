package fr.wildcodeschool.haa.waxym.model;

/**
 * Created by tuffery on 02/12/16.
 */

public class ActivityItemModel {
    private String activityName;
    private String activityColor;

    public ActivityItemModel(String activityName, String activityColor) {
        this.activityName = activityName;
        this.activityColor = activityColor;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityColor() {
        return activityColor;
    }

    public void setActivityColor(String activityColor) {
        this.activityColor = activityColor;
    }
}
