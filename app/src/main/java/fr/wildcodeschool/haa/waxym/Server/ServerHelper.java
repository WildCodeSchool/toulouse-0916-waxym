package fr.wildcodeschool.haa.waxym.Server;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.wildcodeschool.haa.waxym.StatusSingleton;
import fr.wildcodeschool.haa.waxym.SuperInterface;
import fr.wildcodeschool.haa.waxym.model.ActivitiesModel;
import fr.wildcodeschool.haa.waxym.model.ActivityItemModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by tuffery on 13/01/17.
 */

public class ServerHelper {
    public ServerHelper() {
    }

    public  void updateServerListActivities(){
        ArrayList<ActivityItemModel> activitiesList = new ArrayList<>();
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
}
