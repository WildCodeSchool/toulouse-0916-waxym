package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

import fr.wildcodeschool.haa.waxym.Constants;

/**
 * used to create new user on server
 */

public class UserModel {

    @SerializedName(Constants.USER_MODEL_LOGIN)
    String login;

    @SerializedName(Constants.USER_MODEL_PWD)
    String pwd;

    public UserModel(String login, String pwd) {
        this.login = login;
        this.pwd = pwd;
    }
}
