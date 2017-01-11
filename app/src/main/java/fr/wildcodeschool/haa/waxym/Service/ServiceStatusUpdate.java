package fr.wildcodeschool.haa.waxym.Service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import fr.wildcodeschool.haa.waxym.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by devauxarthur on 11/01/2017.
 */

public class ServiceStatusUpdate extends Service {

    private OkHttpClient request;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    private long register (String nom, long pwd){



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final String BASE_URL = "http://api.myservice.com/";

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                new DoBackgroundTask().execute(Utilities.QUERYstatus);
                e.printStackTrace();
            }
            return START_STICKY;
        }
    }


    private class DoBackgroundTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            String dataToSend = params[0];
            Log.i("FROM STATS SERVICE DoBackgroundTask", dataToSend);
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Utilities.AGENT_URL);

            try {
                httpPost.setEntity(new StringEntity(dataToSend, "UTF-8"));

                // Set up the header types needed to properly transfer JSON
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setHeader("Accept-Encoding", "application/json");
                httpPost.setHeader("Accept-Language", "en-US");

                // Execute POST
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity responseEntity = httpResponse.getEntity();
                if (responseEntity != null) {
                    response = EntityUtils.toString(responseEntity);
                } else {
                    response = "{\"NO DATA:\"NO DATA\"}";
                }
            } catch (ClientProtocolException e) {
                response = "{\"ERROR\":" + e.getMessage().toString() + "}";
            } catch (IOException e) {
                response = "{\"ERROR\":" + e.getMessage().toString() + "}";
            }
            return response;
        }



        @Override
        protected void onPostExecute(String result) {
            final Runnable repeat = new Runnable() {
                @Override
                public void run() {
                    new OkHttpClient();
                    new Handler().postDelayed(repeat, 10000);
                }
            }
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    new OkHttpClient();
                }

            }, 10000);
            Utilities.STATUS = result;
            Log.i("FROM STATUS SERVICE: STATUS IS:", Utilities.STATUS);
            super.onPostExecute(result);
        }


    }
}
