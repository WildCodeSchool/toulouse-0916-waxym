package fr.wildcodeschool.haa.waxym.Server;

import android.os.AsyncTask;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.wildcodeschool.haa.waxym.model.IdModel;
import fr.wildcodeschool.haa.waxym.StatusSingleton;
import fr.wildcodeschool.haa.waxym.SuperInterface;
import fr.wildcodeschool.haa.waxym.model.ActivitiesModel;
import fr.wildcodeschool.haa.waxym.model.ActivityItemModel;
import fr.wildcodeschool.haa.waxym.model.ActivityModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by tuffery on 13/01/17.
 */

public class ServerHelper {
    public ServerHelper() {
    }

    public  void updateServerListActivities(){
        SuperInterface apiService = SuperInterface.retrofit.create(SuperInterface.class);
        Call<List<ActivitiesModel>> call =  apiService.getActivities(StatusSingleton.getInstance().getCurrentUserId());
        new ListActivitiesCall().execute(call);


    }
    private class ListActivitiesCall extends AsyncTask<Call, Void, Response<List<ActivitiesModel>>> {
        @Override
        protected Response<List<ActivitiesModel>> doInBackground(Call... params) {
            try {
                Call<List<ActivitiesModel>> call = params[0];
                Response<List<ActivitiesModel>> response = call.execute();

                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Response<List<ActivitiesModel>> result) {
          List<ActivityItemModel> activitieslist =  new ArrayList<>();
            for (int i = 0; i < result.body().size(); i++){
                result.body().get(i);
            }


        }
    }

    public  void addActvityOnServer(){
        SuperInterface apiService = SuperInterface.retrofit.create(SuperInterface.class);
        //
        // ActivityModel rtt = new ActivityModel(0,"RTT","#9FE855"); id = 13
        // ActivityModel SansSolde = new ActivityModel(0,"Sans Soldes","#ce6348"); id = 14
        // ActivityModel cp = new ActivityModel(0,"CP","#55e8b0"); id = 15
        ActivityModel cp = new ActivityModel(0,"CP","#55e8b0");
        Call<IdModel> call =  apiService.addActivity(cp);
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
            List<ActivityItemModel> activitieslist =  new ArrayList<>();



        }
    }

    public  void attachUserToActivity(){
        SuperInterface apiService = SuperInterface.retrofit.create(SuperInterface.class);
        IdModel idModel = new IdModel((long) 13);
        Call<JsonObject> call =  apiService.addActivityToUser(11,idModel);
        new AttachCall().execute(call);
        IdModel idModel2 = new IdModel((long) 14);
        Call<JsonObject> call2 =  apiService.addActivityToUser(11,idModel2);
        new AttachCall().execute(call2);
        IdModel idModel3 = new IdModel((long) 15);
        Call<JsonObject> call3 =  apiService.addActivityToUser(11,idModel3);
        new AttachCall().execute(call3);


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
            List<ActivityItemModel> activitieslist =  new ArrayList<>();



        }
    }
}
