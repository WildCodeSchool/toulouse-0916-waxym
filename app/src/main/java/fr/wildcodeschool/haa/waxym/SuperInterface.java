package fr.wildcodeschool.haa.waxym;


import com.google.gson.JsonObject;


import java.util.List;

import fr.wildcodeschool.haa.waxym.model.ActivitiesModel;
import fr.wildcodeschool.haa.waxym.model.DayActivitiesModel;
import fr.wildcodeschool.haa.waxym.model.UserModel;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by apprenti on 10/01/17.
 */

public interface SuperInterface {

    @GET("activities/{userId}")
    Call<List<ActivitiesModel>> getActivities(@Path("userId") long userId);

    @POST("login")
    Call<IdModel> login(@Body UserModel login);

    @GET("dayactivities/{userId}/{dtstart}/{dtend}")
    Call<JsonObject> getDayActivities(@Path("userId") long userid, @Path("dtstart") long dtstart, @Path("dtend") long dtend );

    @POST("dayactivities/{userId}")
    Call<JsonObject> addActivities(@Body DayActivitiesModel dayActivities);

    @POST("dayactivities/register")
    Call<Long> createRegister(@Body UserModel register);

    @POST("activity")
    Call<JsonObject> addActivity(@Body ActivitiesModel activity);

    @POST("activities/{userId}/add")
    Call<JsonObject> addActivityToUser(@Body ActivitiesModel addActivityToUser);

    @POST("register")
    Call<IdModel> newUser(@Body UserModel userModel);
    public OkHttpClient okClient = new OkHttpClient();
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
