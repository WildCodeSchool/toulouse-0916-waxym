package fr.wildcodeschool.haa.waxym.model;

/**
 * Created by tuffery on 02/12/16.
 */

public class ActivityItemModel {
    private String activityName;
    private String activityColor;
    private long activityNumber;
    private boolean isSelected;

    public ActivityItemModel(long activityNumber,String activityName, String activityColor) {
        this.activityNumber = activityNumber;
        this.activityName = activityName;
        this.activityColor = activityColor;
        this.isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getActivityNumber() {
        return activityNumber;
    }

    public void setActivityNumber(long activityNumber) {
        this.activityNumber = activityNumber;
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
