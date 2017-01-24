package fr.wildcodeschool.haa.waxym;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;

import fr.wildcodeschool.haa.waxym.server.ServerHelper;
import fr.wildcodeschool.haa.waxym.server.ServerInterface;
import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.dataObject.IdDataObject;
import fr.wildcodeschool.haa.waxym.dataObject.UserDataObject;
import retrofit2.Call;
import retrofit2.Response;

/**
 * ask for login information  send to server and check if login succes/fail
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LogActivity";
    private Button btn_login;
    private EditText textEmailAddress;
    private EditText textPassword;
    private MessageDigest digest;
    private String encryptedPassword;
    private ProgressDialog progressDialog;
    private DBHandler mDBHelper;
    private boolean isFirstLaunch = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {


        //no action bar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_log);
        ServerHelper serverHelper = new ServerHelper(this);
        //serverHelper.attachUserToActivity();
        this.mDBHelper = new DBHandler(this);
        // check if database exist
        File database = this.getApplicationContext().getDatabasePath(Constants.DBNAME);
        //copyDatabase(getApplicationContext());

        if (!database.exists()) {
            this.progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme2);
            this.progressDialog.setIndeterminate(true);
            this.progressDialog.setMessage(getString(R.string.initialization_message));
            this.progressDialog.show();
            this.mDBHelper.getReadableDatabase();
            // and copy database with method
            if (!this.copyDatabase(this)) {
                Toast.makeText(this, R.string.database_error_message, Toast.LENGTH_SHORT).show();
                return;
            }

        }


        this.btn_login = (Button) findViewById(R.id.btn_login);
        this.textEmailAddress = (EditText) findViewById(R.id.input_email);

        this.textPassword = (EditText) findViewById(R.id.input_password);
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textEmailAddress.getText().toString().isEmpty() || textPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), R.string.wrong_login_message, Toast.LENGTH_LONG).show();
                } else {
                    login();
                }
            }
        });


    }

    public void login() {
        Log.d(TAG, "Login");


        this.encryptedPassword = shaConverter(this.textPassword.getText().toString());

        ServerInterface apiService = ServerInterface.retrofit.create(ServerInterface.class);
        UserDataObject UserDataObject = new UserDataObject(textEmailAddress.getText().toString(), this.encryptedPassword);
        Call<IdDataObject> call = apiService.login(UserDataObject);
        new NetworkCall().execute(call);


        btn_login.setEnabled(false);

        this.progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme2);
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.setMessage(getString(R.string.auth_message));
        this.progressDialog.show();


    }

    protected String shaConverter(String passwordToEncrypt) {
        try {
            digest = MessageDigest.getInstance(Constants.SHA);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest.update(passwordToEncrypt.toString().getBytes());
        byte[] hash = digest.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();

    }


    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {

        finish();
        Intent intent = new Intent(this, DataEntryActivity.class);
        startActivity(intent);
        this.progressDialog.dismiss();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), R.string.login_fail_message, Toast.LENGTH_LONG).show();

        btn_login.setEnabled(true);
        this.progressDialog.dismiss();
    }

    private class NetworkCall extends AsyncTask<Call, Void, Response<IdDataObject>> {
        boolean isTimeOut = false;

        @Override
        protected Response<IdDataObject> doInBackground(Call... params) {
            try {
                Call<IdDataObject> call = params[0];
                Response<IdDataObject> response = call.execute();
                return response;
            } catch (IOException e) {
                e.printStackTrace();
                this.isTimeOut = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Response<IdDataObject> result) {
            if (!isTimeOut) {
                if (result.body().getID() != Constants.BLANK_HOLIDAY) {

                    ArrayList<Long> existingUserId = mDBHelper.getAllUsers();
                    boolean isExisting = false;
                    for (int i = 0; i < existingUserId.size(); i++) {
                        if (existingUserId.get(i) == result.body().getID()) {
                            isExisting = true;
                            break;
                        }
                    }
                    if (!isExisting) {
                        mDBHelper.newUser(result.body().getID(), textEmailAddress.getText().toString());
                    }

                    StatusSingleton status = StatusSingleton.getInstance();
                    status.setCurrentUserId(result.body().getID());
                    ServerHelper serverHelper = new ServerHelper(getApplicationContext());
                    try {
                        if (mDBHelper.getNotSendedActivities().size() == 0) {
                            serverHelper.updateServerListActivities();
                        } else {
                            serverHelper.sendDaysActivities();
                            if (mDBHelper.getNotSendedActivities().size() == 0) {
                                serverHelper.updateServerListActivities();
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (isFirstLaunch) {
                        serverHelper.getSavedActivitiesFromServer();
                    }else try {
                        if (mDBHelper.getNotSendedActivities().size() >0){
                            serverHelper.sendDaysActivities();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    onLoginSuccess();
                } else {
                    onLoginFailed();
                }
            } else {
                onLoginFailed();
            }
        }
    }

    //copying database from assets to database folder
    private boolean copyDatabase(Context context) {
        this.isFirstLaunch = true;
        try {
            InputStream inpuStream = context.getAssets().open(Constants.DBNAME);
            // set target of output
            OutputStream outputStream = new FileOutputStream(getDatabasePath(Constants.DBNAME));
            // buffer
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inpuStream.read(buff)) > 0) {
                //writing
                outputStream.write(buff, 0, length);

            }
            //clear buffer
            outputStream.flush();
            outputStream.close();
            this.progressDialog.dismiss();
            Log.w("MainActivity", "DB copied");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
