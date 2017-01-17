package fr.wildcodeschool.haa.waxym.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devauxarthur on 10/01/2017.
 */

public class UserModel {

    @SerializedName("login")
    String login;

    @SerializedName("pwd")
    String pwd;

    public UserModel(String login, String pwd) {
        this.login = login;
        this.pwd = pwd;
    }
}
