package fr.wildcodeschool.haa.waxym.dataObject;

import com.google.gson.annotations.SerializedName;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * used to communicate with server an id
 */

public class IdDataObject {
    @SerializedName(Constants.ID_MODEL)
    Long ID;

    public IdDataObject(Long ID) {
        this.ID = ID;
    }

    public Long getID() {
        return ID;
    }
}
