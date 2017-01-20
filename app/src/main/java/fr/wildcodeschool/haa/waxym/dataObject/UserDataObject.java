package fr.wildcodeschool.haa.waxym.dataObject;

import com.google.gson.annotations.SerializedName;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * used to create new user on server
 */

public class UserDataObject {

    @SerializedName(Constants.USER_MODEL_LOGIN)
    String login;

    @SerializedName(Constants.USER_MODEL_PWD)
    String pwd;

    public UserDataObject(String login, String pwd) {
        this.login = login;
        this.pwd = pwd;
    }
}
