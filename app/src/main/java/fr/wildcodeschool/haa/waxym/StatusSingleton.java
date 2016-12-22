package fr.wildcodeschool.haa.waxym;

/**
 * Created by tuffery on 21/12/16.
 */

public class StatusSingleton {
    private static StatusSingleton mInstance = null;
    private  boolean isMenuCreated;
    private  boolean isEditMode;

    private StatusSingleton() {
        isEditMode = false;
        isMenuCreated = false;
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
