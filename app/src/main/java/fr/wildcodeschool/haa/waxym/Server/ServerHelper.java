package fr.wildcodeschool.haa.waxym.Server;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.model.IdModel;
import fr.wildcodeschool.haa.waxym.StatusSingleton;
import fr.wildcodeschool.haa.waxym.SuperInterface;
import fr.wildcodeschool.haa.waxym.model.ActivitiesModel;
import fr.wildcodeschool.haa.waxym.model.ActivityItemModel;
import fr.wildcodeschool.haa.waxym.model.ActivityModel;
import fr.wildcodeschool.haa.waxym.model.ListOfActivitiesModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by tuffery on 13/01/17.
 */

public class ServerHelper {
    Context context;

    public ServerHelper(Context context) {
        this.context = context;
    }

    public void updateServerListActivities() {
        SuperInterface apiService = SuperInterface.retrofit.create(SuperInterface.class);
        Call<ListOfActivitiesModel> call = apiService.getActivities(11);
        new ListActivitiesCall().execute(call);


    }

    private class ListActivitiesCall extends AsyncTask<Call, Void, Response<ListOfActivitiesModel>> {
        @Override
        protected Response<ListOfActivitiesModel> doInBackground(Call... params) {
            try {
                Call<ListOfActivitiesModel> call = params[0];
                Response<ListOfActivitiesModel> response = call.execute();

                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Response<ListOfActivitiesModel> result) {
            ArrayList<ActivitiesModel> activitieslist = result.body().getListOfActivities();
            for (int i = 0; i < activitieslist.size(); i++) {
                activitieslist.get(i);
            }
            DBHandler mDBHandler = new DBHandler(context);
            mDBHandler.updateActivitiesList(activitieslist);

        }
    }

    public void addActvityOnServer() {
        SuperInterface apiService = SuperInterface.retrofit.create(SuperInterface.class);
        //
        // ActivityModel rtt = new ActivityModel(0,"RTT","#9FE855"); id = 13
        // ActivityModel SansSolde = new ActivityModel(0,"Sans Soldes","#ce6348"); id = 14
        // ActivityModel cp = new ActivityModel(0,"CP","#55e8b0"); id = 15
        // ActivityModel cp = new ActivityModel(1,"CP","#55e8b0"); id = 16
        //ActivityModel cp = new ActivityModel(1,"Sans Solde","#ce6348"); id = 17
        ActivityModel cp = new ActivityModel(1, "Sans Solde", "#ce6348");
        Call<IdModel> call = apiService.addActivity(cp);
        new AddActivityCall().execute(call);


    }

    private class AddActivityCall extends AsyncTask<Call, Void, Response<IdModel>> {
        @Override
        protected Response<IdModel> doInBackground(Call... params) {
            try {
                Call<IdModel> call = params[0];
                Response<IdModel> response = call.execute();

                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Response<IdModel> result) {
            List<ActivityItemModel> activitieslist = new ArrayList<>();


        }
    }

    public void detachUserToActivity() {
        SuperInterface apiService = SuperInterface.retrofit.create(SuperInterface.class);
        IdModel idModel = new IdModel((long) 13);
        Call<JsonObject> call = apiService.removeActivityToUser(11, idModel);
        new AttachCall().execute(call);
        IdModel idModel2 = new IdModel((long) 14);
        Call<JsonObject> call2 = apiService.removeActivityToUser(11, idModel2);
        new AttachCall().execute(call2);
        IdModel idModel3 = new IdModel((long) 15);
        Call<JsonObject> call3 = apiService.removeActivityToUser(11, idModel3);
        new AttachCall().execute(call3);
    }

    public void attachUserToActivity() {
        SuperInterface apiService = SuperInterface.retrofit.create(SuperInterface.class);
        IdModel idModel = new IdModel((long) 4);
        Call<JsonObject> call = apiService.addActivityToUser(11, idModel);
        new AttachCall().execute(call);
        IdModel idModel2 = new IdModel((long) 5);
        Call<JsonObject> call2 = apiService.addActivityToUser(11, idModel2);
        new AttachCall().execute(call2);
        IdModel idModel3 = new IdModel((long) 6);
        Call<JsonObject> call3 = apiService.addActivityToUser(11, idModel3);
        new AttachCall().execute(call3);
        IdModel idModel4 = new IdModel((long) 7);
        Call<JsonObject> call4 = apiService.addActivityToUser(11, idModel4);
        new AttachCall().execute(call4);
        IdModel idModel5 = new IdModel((long) 8);
        Call<JsonObject> call5 = apiService.addActivityToUser(11, idModel5);
        new AttachCall().execute(call5);
        IdModel idModel6 = new IdModel((long) 9);
        Call<JsonObject> call6 = apiService.addActivityToUser(11, idModel6);
        new AttachCall().execute(call6);
        IdModel idModel7 = new IdModel((long) 10);
        Call<JsonObject> call7 = apiService.addActivityToUser(11, idModel7);
        new AttachCall().execute(call7);
        IdModel idModel8 = new IdModel((long) 10);
        Call<JsonObject> call8 = apiService.addActivityToUser(11, idModel8);
        new AttachCall().execute(call8);


    }

    private class AttachCall extends AsyncTask<Call, Void, Response<JsonObject>> {
        @Override
        protected Response<JsonObject> doInBackground(Call... params) {
            try {
                Call<JsonObject> call = params[0];
                Response<JsonObject> response = call.execute();

                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Response<JsonObject> result) {
            List<ActivityItemModel> activitieslist = new ArrayList<>();


        }
    }
}
