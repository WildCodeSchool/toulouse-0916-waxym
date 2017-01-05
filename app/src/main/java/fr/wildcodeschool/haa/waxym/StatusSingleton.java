package fr.wildcodeschool.haa.waxym;

import java.util.Calendar;

/**
 * Created by tuffery on 21/12/16.
 */

public class StatusSingleton {
    private static StatusSingleton mInstance = null;
    private  boolean isMenuCreated;
    private  boolean isEditMode;
    private boolean isInMonthView;
    private boolean isInDayView;
    private Calendar currentDate;
    private int lastMonthPosition;

    private StatusSingleton() {
        isEditMode = false;
        isMenuCreated = false;
        isInMonthView = true;
        isInDayView = false;
        this.currentDate = Calendar.getInstance();
    }
    public static StatusSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new StatusSingleton();
        }
        return mInstance;
    }

    public boolean isMenuCreated() {
        return isMenuCreated;
    }

    public boolean isInMonthView() {
        return isInMonthView;
    }

    public void setInMonthView(boolean inMonthView) {
        isInMonthView = inMonthView;
    }

    public void setMenuCreated(boolean menuCreated) {
        isMenuCreated = menuCreated;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    public boolean isInDayView() {
        return isInDayView;
    }

    public void setInDayView(boolean inDayView) {
        isInDayView = inDayView;
    }

    public Calendar getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentDate = currentDate;
    }

    public int getLastMonthPosition() {
        return lastMonthPosition;
    }

    public void setLastMonthPosition(int lastMonthPosition) {
        this.lastMonthPosition = lastMonthPosition;
    }
}
