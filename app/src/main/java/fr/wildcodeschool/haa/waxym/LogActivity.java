package fr.wildcodeschool.haa.waxym;

import android.app.ProgressDialog;
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

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    Headers header;
    long resultCode;
    long idTest;
    String headTest;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        /*Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();*/


        //no action bar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_log);
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

        encryptedPassword = sb.toString();

        SuperInterface apiService = SuperInterface.retrofit.create(SuperInterface.class);
        UserModel userModel = new UserModel(textEmailAddress.getText().toString(), this.encryptedPassword);
        Call<IdModel> call = apiService.login(userModel);
        new NetworkCall().execute(call);

        if (!validate()) {
            onLoginFailed();
            return;
        }

        btn_login.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LogActivity.this,
                R.style.AppTheme2);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = textEmailAddress.getText().toString();
        String password = textPassword.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed

                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 1);
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
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btn_login.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = textEmailAddress.getText().toString();
        String password = textPassword.getText().toString();

       /* *//*if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textEmailAddress.setError("enter a valid email address");
            valid = false;
        } else {
            textEmailAddress.setError(null);*//*
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            textPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            textPassword.setError(null);
        }*/

        return valid;
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
                StatusSingleton status = StatusSingleton.getInstance();
                status.setCurrentUserId(result.body().getID());
                onLoginSuccess();
            } else {
                onLoginFailed();
            }
        }
    }
}
