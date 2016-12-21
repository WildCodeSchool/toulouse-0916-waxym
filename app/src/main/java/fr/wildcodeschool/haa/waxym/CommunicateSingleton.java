package fr.wildcodeschool.haa.waxym;

/**
 * Created by tuffery on 21/12/16.
 */

public class CommunicateSingleton {
    private static CommunicateSingleton mInstance = null;
    private  boolean isMenuCreated;
    private  boolean isEditMode;

    private CommunicateSingleton() {
        isEditMode = false;
        isMenuCreated = false;
    }
    public static CommunicateSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new CommunicateSingleton();
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
