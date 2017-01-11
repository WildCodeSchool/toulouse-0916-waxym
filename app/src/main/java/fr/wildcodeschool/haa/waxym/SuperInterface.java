package fr.wildcodeschool.haa.waxym;

import com.google.gson.JsonObject;

import fr.wildcodeschool.haa.waxym.model.ActivitiesModel;
import fr.wildcodeschool.haa.waxym.model.DayActivitiesModel;
import fr.wildcodeschool.haa.waxym.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by apprenti on 10/01/17.
 */

public interface SuperInterface {

    @GET("activities/{userId}")
    Call<JsonObject> getActivities(@Path("userId") long userId);

    @POST("login")
    Call<JsonObject> postlogin(@Body UserModel login);

    @GET("dayactivities/{userId}/{dtstart}/{dtend}")
    Call<JsonObject> getDayActivities(@Path("userId") long userid, @Path("dtstart") long dtstart, @Path("dtend") long dtend );

    @POST("dayactivities/{userId}")
    Call<JsonObject> addActivities(@Body DayActivitiesModel dayActivities);

    @POST("dayactivities/register")
    Call<JsonObject> createRegister(@Body UserModel register);

    @POST("activity")
    Call<JsonObject> addActivity(@Body ActivitiesModel activity);

    @POST("activities/{userId}/add")
    Call<JsonObject> addUser(@Body ActivitiesModel addUser);

}
