package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devauxarthur on 10/01/2017.
 */

public class ActivitiesModel {

    @SerializedName("id")
    long id;

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

    public ActivitiesModel(long id, int type, String label, long clientId, long contractId, String color) {
        this.id = id;
        this.type = type;
        this.label = label;
        this.clientId = clientId;
        this.contractId = contractId;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public long getClientId() {
        return clientId;
    }

    public long getContractId() {
        return contractId;
    }

    public String getColor() {
        return color;
    }
}
