package fr.wildcodeschool.haa.waxym.server;


import com.google.gson.JsonObject;


import fr.wildcodeschool.haa.waxym.Constants;
import fr.wildcodeschool.haa.waxym.dataObject.ActivityDataobject;
import fr.wildcodeschool.haa.waxym.dataObject.IdDataObject;
import fr.wildcodeschool.haa.waxym.dataObject.ListOfActivitiesDataObject;
import fr.wildcodeschool.haa.waxym.dataObject.ListOfDayActivitiesDataObject;
import fr.wildcodeschool.haa.waxym.dataObject.UserDataObject;
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
    Call<ListOfActivitiesDataObject> getActivities(@Path(Constants.SERVER_USERID_PATH) long userId);

    @POST(Constants.LOGIN_BODY)
    Call<IdDataObject> login(@Body UserDataObject login);

    @GET(Constants.GET_DAY_ACTIVITIES_PATH)
    Call<ListOfDayActivitiesDataObject> getDayActivities(@Path(Constants.SERVER_USERID_PATH) long userid, @Path(Constants.DTSTART_PATH) String dtstart, @Path(Constants.DTEND_PATH) String dtend );

    @POST(Constants.SEND_ACTIVITIES_PATH )
    Call<JsonObject> sendActivities(@Path(Constants.SERVER_USERID_PATH) long userId ,@Body ListOfDayActivitiesDataObject listOfDayActivitiesDataObject);

    @POST(Constants.CREATE_REGISTER_BODY)
    Call<IdDataObject> register(@Body UserDataObject register);

    @POST(Constants.ADD_ACTIVITY_BODY)
    Call<IdDataObject> addActivity(@Body ActivityDataobject activity);

    @POST(Constants.ADD_ACTIVITY_TO_USER)
    Call<JsonObject> addActivityToUser(@Path(Constants.SERVER_USERID_PATH) long userId,
                                       @Body IdDataObject idActivity);
    @POST(Constants.REMOVE_ACTIVITY_TO_USER)
    Call<JsonObject> removeActivityToUser(@Path(Constants.SERVER_USERID_PATH) long userId,
                                       @Body IdDataObject idActivity);
    @POST(Constants.NEWUSER_BODY)
    Call<IdDataObject> newUser(@Body UserDataObject UserDataObject);
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
