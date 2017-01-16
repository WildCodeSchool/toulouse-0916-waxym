package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tuffery on 11/01/17.
 */

public class IdModel {
    @SerializedName("id")
    Long userID;

    public IdModel(Long login) {
        this.userID = login;
    }

    public Long getUserID() {
        return userID;
    }
}
