package fr.wildcodeschool.haa.waxym.server;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.wildcodeschool.haa.waxym.Constants;
import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.dataObject.DayActivitiesDataObject;
import fr.wildcodeschool.haa.waxym.model.DayStuffModel;
import fr.wildcodeschool.haa.waxym.dataObject.IdDataObject;
import fr.wildcodeschool.haa.waxym.StatusSingleton;
import fr.wildcodeschool.haa.waxym.dataObject.ActivitiesDataObject;
import fr.wildcodeschool.haa.waxym.model.ActivityItemModel;
import fr.wildcodeschool.haa.waxym.dataObject.ActivityDataobject;
import fr.wildcodeschool.haa.waxym.dataObject.ListOfActivitiesDataObject;
import fr.wildcodeschool.haa.waxym.dataObject.ListOfDayActivitiesDataObject;
import retrofit2.Call;
import retrofit2.Response;

/**
 * List of methods to interact asynchronous with server with Json (using retrofit)
 */

public class ServerHelper {
    Context context;

    public ServerHelper(Context context) {
        this.context = context;
    }

    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);

    public void updateServerListActivities() {
        ServerInterface apiService = ServerInterface.retrofit.create(ServerInterface.class);
        Call<ListOfActivitiesDataObject> call = apiService.getActivities(11);
        new ListActivitiesCall().execute(call);


    }

    private class ListActivitiesCall extends AsyncTask<Call, Void, Response<ListOfActivitiesDataObject>> {
        @Override
        protected Response<ListOfActivitiesDataObject> doInBackground(Call... params) {
            try {
                Call<ListOfActivitiesDataObject> call = params[0];
                Response<ListOfActivitiesDataObject> response = call.execute();

                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Response<ListOfActivitiesDataObject> result) {
            ArrayList<ActivitiesDataObject> activitieslist = result.body().getListOfActivities();
            for (int i = 0; i < activitieslist.size(); i++) {
                activitieslist.get(i);
            }
            DBHandler mDBHandler = new DBHandler(context);
            mDBHandler.updateActivitiesList(activitieslist);

        }
    }

    public void addActvityOnServer() {
        ServerInterface apiService = ServerInterface.retrofit.create(ServerInterface.class);
        //
        //ActivityModel rtt = new ActivityModel(0,"RTT","#9FE855"); id = 13
        // ActivityModel SansSolde = new ActivityModel(0,"Sans Soldes","#ce6348"); id = 14
        // ActivityModel cp = new ActivityModel(0,"CP","#55e8b0"); id = 15
        // ActivityModel cp = new ActivityModel(1,"CP","#55e8b0"); id = 16
        //ActivityModel cp = new ActivityModel(1 ,"RTT","#9FE855"); id = 17 !
        //
        ActivityDataobject cp = new ActivityDataobject(1 ,"RTT","#9FE855");
        Call<IdDataObject> call = apiService.addActivity(cp);
        new AddActivityCall().execute(call);


    }

    private class AddActivityCall extends AsyncTask<Call, Void, Response<IdDataObject>> {
        @Override
        protected Response<IdDataObject> doInBackground(Call... params) {
            try {
                Call<IdDataObject> call = params[0];
                Response<IdDataObject> response = call.execute();

                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Response<IdDataObject> result) {
            List<ActivityItemModel> activitieslist = new ArrayList<>();


        }
    }

    public void detachUserToActivity() {
        ServerInterface apiService = ServerInterface.retrofit.create(ServerInterface.class);
        IdDataObject idDataObject = new IdDataObject((long) 13);
        Call<JsonObject> call = apiService.removeActivityToUser(11, idDataObject);
        new AttachCall().execute(call);
        IdDataObject idDataObject2 = new IdDataObject((long) 14);
        Call<JsonObject> call2 = apiService.removeActivityToUser(11, idDataObject2);
        new AttachCall().execute(call2);
        IdDataObject idDataObject3 = new IdDataObject((long) 15);
        Call<JsonObject> call3 = apiService.removeActivityToUser(11, idDataObject3);
        new AttachCall().execute(call3);
    }

    public void attachUserToActivity() {
        ServerInterface apiService = ServerInterface.retrofit.create(ServerInterface.class);
        IdDataObject idDataObject = new IdDataObject((long) 4);
        Call<JsonObject> call = apiService.addActivityToUser(11, idDataObject);
        new AttachCall().execute(call);
        IdDataObject idDataObject2 = new IdDataObject((long) 5);
        Call<JsonObject> call2 = apiService.addActivityToUser(11, idDataObject2);
        new AttachCall().execute(call2);
        IdDataObject idDataObject3 = new IdDataObject((long) 6);
        Call<JsonObject> call3 = apiService.addActivityToUser(11, idDataObject3);
        new AttachCall().execute(call3);
        IdDataObject idDataObject4 = new IdDataObject((long) 7);
        Call<JsonObject> call4 = apiService.addActivityToUser(11, idDataObject4);
        new AttachCall().execute(call4);
        IdDataObject idDataObject5 = new IdDataObject((long) 8);
        Call<JsonObject> call5 = apiService.addActivityToUser(11, idDataObject5);
        new AttachCall().execute(call5);
        IdDataObject idDataObject6 = new IdDataObject((long) 9);
        Call<JsonObject> call6 = apiService.addActivityToUser(11, idDataObject6);
        new AttachCall().execute(call6);
        IdDataObject idDataObject7 = new IdDataObject((long) 10);
        Call<JsonObject> call7 = apiService.addActivityToUser(11, idDataObject7);
        new AttachCall().execute(call7);
        IdDataObject idDataObject8 = new IdDataObject((long) 10);
        Call<JsonObject> call8 = apiService.addActivityToUser(11, idDataObject8);
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

    public void sendDaysActivities() throws ParseException {
        ServerInterface apiService = ServerInterface.retrofit.create(ServerInterface.class);
        DBHandler mDBHandler = new DBHandler(this.context);
        ArrayList<DayStuffModel> originalList = mDBHandler.getNotSendedActivities();
        HashMap<String, DayActivitiesDataObject> convertedListTosend = new HashMap<>();
        ArrayList<DayActivitiesDataObject> finalListTosend = new ArrayList<>();
        DayActivitiesDataObject dayActivitiesModel;
        // converting DaystuffModel in DayAtivitiesModel
        for (int i = 0; i < originalList.size(); i++) {
            dayActivitiesModel = convertedListTosend.get(this.sdf.format(originalList.get(i).getDate()));
            if (originalList.get(i).getAfternoon() == 1) {

                if (dayActivitiesModel != null) {
                    dayActivitiesModel.setPmActivityId(originalList.get(i).getActivityId());
                    convertedListTosend.put(this.sdf.format(originalList.get(i).getDate()), dayActivitiesModel);
                } else {
                    convertedListTosend.put(this.sdf.format(originalList.get(i).getDate()), new DayActivitiesDataObject(originalList.get(i).getActivityId(), this.sdf.format(originalList.get(i).getDate())));
                }

            } else {
                if (dayActivitiesModel != null) {
                    dayActivitiesModel.setAmActivityId(originalList.get(i).getActivityId());
                    convertedListTosend.put(this.sdf.format(originalList.get(i).getDate()), dayActivitiesModel);
                } else {
                    convertedListTosend.put(this.sdf.format(originalList.get(i).getDate()), new DayActivitiesDataObject(this.sdf.format(originalList.get(i).getDate()), originalList.get(i).getActivityId()));

                }
            }
        }
        for (String str : convertedListTosend.keySet()){
            finalListTosend.add(convertedListTosend.get(str));
        }
        ListOfDayActivitiesDataObject listOfDayActivitiesDataObject = new ListOfDayActivitiesDataObject(finalListTosend);
        Call<JsonObject> call = apiService.sendActivities(StatusSingleton.getInstance().getCurrentUserId(), listOfDayActivitiesDataObject);
        new SendActivitiesCall().execute(call);
    }

    private class SendActivitiesCall extends AsyncTask<Call, Void, Response<JsonObject>> {
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

    public void getActivitiesFromServer(){
        ServerInterface apiService = ServerInterface.retrofit.create(ServerInterface.class);
        Call<ListOfDayActivitiesDataObject> call = apiService.getDayActivities(StatusSingleton.getInstance().getCurrentUserId(),"2017-01-01","2017-01-31");
        new GetActivitiesCall().execute(call);
    }

    private class GetActivitiesCall extends AsyncTask<Call, Void, Response<ListOfDayActivitiesDataObject>> {
        @Override
        protected Response<ListOfDayActivitiesDataObject> doInBackground(Call... params) {
            try {
                Call<ListOfDayActivitiesDataObject> call = params[0];
                Response<ListOfDayActivitiesDataObject> response = call.execute();

                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Response<ListOfDayActivitiesDataObject> result) {
            List<ActivityItemModel> activitieslist = new ArrayList<>();


        }
    }

}
