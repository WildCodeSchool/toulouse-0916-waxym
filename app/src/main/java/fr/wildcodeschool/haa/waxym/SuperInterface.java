package fr.wildcodeschool.haa.waxym;

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
    Call<ActivitiesModel> getActivities(@Path("userId") long userId);

    @POST("login")
    Call<UserModel> postlogin(@Body UserModel login);

    @GET("dayactivities/{userId}/{dtstart}/{dtend}")
    Call<DayActivitiesModel> getDayActivities(@Path("userId") long userid, @Path("dtstart") long dtstart, @Path("dtend") long dtend );

    @POST("dayactivities/{userId}")
    Call<DayActivitiesModel> addActivities(@Body DayActivitiesModel dayActivities);

    @POST("dayactivities/register")
    Call<UserModel> createRegister(@Body UserModel register);

    @POST("activity")
    Call<ActivitiesModel> addActivity(@Body ActivitiesModel activity);

    @POST("activities/{userId}/add")
    Call<ActivitiesModel> addUser(@Body ActivitiesModel addUser);

}
