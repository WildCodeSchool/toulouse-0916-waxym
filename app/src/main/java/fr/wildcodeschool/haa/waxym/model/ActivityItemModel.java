package fr.wildcodeschool.haa.waxym.model;

/**
 * model used to generate itemList of contracts
 */

public class ActivityItemModel {
    private String activityName;
    private String activityColor;
    private long activityNumber;
    private long activityID;
    private boolean isSelected;

    public ActivityItemModel(long activityNumber, String activityName, String activityColor, long activityID) {
        this.activityNumber = activityNumber;
        this.activityName = activityName;
        this.activityColor = activityColor;
        this.activityID = activityID;
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

    public long getActivityID() {
        return activityID;
    }

    public void setActivityID(long activityID) {
        this.activityID = activityID;
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
