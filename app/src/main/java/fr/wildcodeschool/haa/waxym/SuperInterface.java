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
    Call<UserModel> login(@Body UserModel login);

   // @GET("activities/{userId}");
   // Call<> activities(@Path(""))

    @GET("http://www.waxym.com/employeah/v1/dayactivities/{userId}/{dtstart}/{dtend}")
    Call<DayActivitiesModel> getUserId();

    @POST("http://www.waxym.com/employeah/v1/dayactivities/{userId}")
    Call<DayActivitiesModel> addUserId(@Body DayActivitiesModel );

    @POST("dayactivities/register")
    Call<UserModel> createregister(@Body UserModel register);

}
