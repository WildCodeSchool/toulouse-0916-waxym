package fr.wildcodeschool.haa.waxym.server;


import com.google.gson.JsonObject;


import fr.wildcodeschool.haa.waxym.Constants;
import fr.wildcodeschool.haa.waxym.model.ActivityModel;
import fr.wildcodeschool.haa.waxym.model.IdModel;
import fr.wildcodeschool.haa.waxym.model.ListOfActivitiesModel;
import fr.wildcodeschool.haa.waxym.model.ListOfDayActivitiesModel;
import fr.wildcodeschool.haa.waxym.model.UserModel;
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

public interface ServerInterface {

    @GET(Constants.GET_ACTIVITIES_PATH)
    Call<ListOfActivitiesModel> getActivities(@Path(Constants.SERVER_USERID_PATH) long userId);

    @POST(Constants.LOGIN_BODY)
    Call<IdModel> login(@Body UserModel login);

    @GET(Constants.GET_DAY_ACTIVITIES_PATH)
    Call<ListOfDayActivitiesModel> getDayActivities(@Path(Constants.SERVER_USERID_PATH) long userid, @Path(Constants.DTSTART_PATH) String dtstart, @Path(Constants.DTEND_PATH) String dtend );

    @POST(Constants.SEND_ACTIVITIES_PATH )
    Call<JsonObject> sendActivities(@Path(Constants.SERVER_USERID_PATH) long userId ,@Body ListOfDayActivitiesModel listOfDayActivitiesModel);

    @POST(Constants.CREATE_REGISTER_BODY)
    Call<Long> createRegister(@Body UserModel register);

    @POST(Constants.ADD_ACTIVITY_BODY)
    Call<IdModel> addActivity(@Body ActivityModel activity);

    @POST(Constants.ADD_ACTIVITY_TO_USER)
    Call<JsonObject> addActivityToUser(@Path(Constants.SERVER_USERID_PATH) long userId,
                                       @Body IdModel idActivity);
    @POST(Constants.REMOVE_ACTIVITY_TO_USER)
    Call<JsonObject> removeActivityToUser(@Path(Constants.SERVER_USERID_PATH) long userId,
                                       @Body IdModel idActivity);
    @POST(Constants.NEWUSER_BODY)
    Call<IdModel> newUser(@Body UserModel userModel);
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
