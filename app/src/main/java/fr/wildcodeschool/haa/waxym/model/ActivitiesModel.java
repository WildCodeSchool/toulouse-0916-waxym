package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * Created by devauxarthur on 10/01/2017.
 */

public class ActivitiesModel {

    @SerializedName(Constants.ACTIVITIES_MODEL_ID)
    long id;

    @SerializedName(Constants.ACTIVITIES_MODEL_TYPE)
    int type;

    @SerializedName(Constants.ACTIVITIES_MODEL_LABEL)
    String label;

    @SerializedName(Constants.ACTIVITIES_MODEL_CLIENT_ID)
    long clientId;

    @SerializedName(Constants.ACTIVITIES_MODEL_CONTRACT_ID)
    long contractId;

    @SerializedName(Constants.ACTIVITIES_MODEL_COLOR)
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
