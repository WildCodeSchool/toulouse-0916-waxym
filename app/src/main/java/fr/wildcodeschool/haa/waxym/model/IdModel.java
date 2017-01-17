package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * Created by tuffery on 11/01/17.
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
