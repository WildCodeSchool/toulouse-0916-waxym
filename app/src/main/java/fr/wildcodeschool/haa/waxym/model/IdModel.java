package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * used to communicate with server an id
 */

public class IdModel {
    @SerializedName(Constants.ID_MODEL)
    Long ID;

    public IdModel(Long ID) {
        this.ID = ID;
    }

    public Long getID() {
        return ID;
    }
}
