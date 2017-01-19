package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * Model used to create new activity on server
 */

public class ActivityModel {

    @SerializedName(Constants.ACTIVITY_MODEL_TYPE)
    int type;

    @SerializedName(Constants.ACTIVITY_MODEL_LABEL)
    String label;

    @SerializedName(Constants.ACTIVITY_MODEL_CLIENT_ID)
    long clientId;

    @SerializedName(Constants.ACTIVITY_MODEL_CONTRACT_ID)
    long contractId;

    @SerializedName(Constants.ACTIVITY_MODEL_COLOR)
    String color;

    public ActivityModel(int type, String label, String color) {
        this.label = label;
        this.type = type;
        this.color = color;
    }

    public ActivityModel(int type, String label, long clientId, long contractId, String color) {
        this.type = type;
        this.label = label;
        this.clientId = clientId;
        this.contractId = contractId;
        this.color = color;
    }
}
