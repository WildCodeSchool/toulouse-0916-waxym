package fr.wildcodeschool.haa.waxym;

/**
 * Created by tuffery on 21/12/16.
 */

public class StatusSingleton {
    private static StatusSingleton mInstance = null;
    private  boolean isMenuCreated;
    private  boolean isEditMode;
    private boolean isInMonthView;

    private StatusSingleton() {
        isEditMode = false;
        isMenuCreated = false;
        isInMonthView = true;
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
}
