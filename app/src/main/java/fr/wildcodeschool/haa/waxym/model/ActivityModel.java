package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devauxarthur on 10/01/2017.
 */

public class ActivityModel {

    @SerializedName("type")
    int type;

    @SerializedName("label")
    String label;

    @SerializedName("clientId")
    long clientId;

    @SerializedName("contractId")
    long contractId;

    @SerializedName("color")
    String color;

    public ActivityModel( int type, String label, String color) {
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
