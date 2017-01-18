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
import java.util.ArrayList;

import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.model.IdModel;
import fr.wildcodeschool.haa.waxym.model.UserModel;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Response;


public class LogActivity extends AppCompatActivity {
    private static final String TAG = "LogActivity";
    private Button btn_login;
    private EditText textEmailAddress;
    private EditText textPassword;
    private MessageDigest digest;
    private String encryptedPassword;
    private ProgressDialog progressDialog;
    private DBHandler mDBHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {


        //no action bar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_log);

        this.mDBHelper = new DBHandler(this);
        // check if database exist
        File database = this.getApplicationContext().getDatabasePath(Constants.DBNAME);
        copyDatabase(getApplicationContext());
        if (!database.exists()) {
            this.mDBHelper.getReadableDatabase();
            // and copy database with method
            if (!this.copyDatabase(this)) {
                Toast.makeText(this, "error cannot copy Database", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getBaseContext(), "login ou mot de passe vide", Toast.LENGTH_LONG).show();
                } else {
                    login();
                }
            }
        });


    }

    public void login() {
        Log.d(TAG, "Login");

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest.update(textPassword.getText().toString().getBytes());
        byte[] hash = digest.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }

        this.encryptedPassword = sb.toString();

        SuperInterface apiService = SuperInterface.retrofit.create(SuperInterface.class);
        UserModel userModel = new UserModel(textEmailAddress.getText().toString(), this.encryptedPassword);
        Call<IdModel> call = apiService.login(userModel);
        new NetworkCall().execute(call);


        btn_login.setEnabled(false);

        this.progressDialog = new ProgressDialog(LogActivity.this,
                R.style.AppTheme2);
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.setMessage("Authenticating...");
        this.progressDialog.show();


    }


    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        btn_login.setEnabled(true);
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.progressDialog.dismiss();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btn_login.setEnabled(true);
        this.progressDialog.dismiss();
    }

    private class NetworkCall extends AsyncTask<Call, Void, Response<IdModel>> {
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

            if (result.body().getID() != -1) {

               ArrayList<Long> existingUserId = mDBHelper.getAllUsers();
                boolean isExisting = false;
                for (int i = 0; i < existingUserId.size(); i++) {
                    if (existingUserId.get(i) == result.body().getID()) {
                        isExisting = true;
                        break;
                    }
                }
                    if (!isExisting){
                        mDBHelper.newUser(result.body().getID(), textEmailAddress.getText().toString());
                    }

                StatusSingleton status = StatusSingleton.getInstance();
                status.setCurrentUserId(result.body().getID());
                onLoginSuccess();
            } else {
                onLoginFailed();
            }
        }
    }
    //copying database from assets to database folder
    private boolean copyDatabase(Context context) {
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
            Log.w("MainActivity", "DB copied");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
