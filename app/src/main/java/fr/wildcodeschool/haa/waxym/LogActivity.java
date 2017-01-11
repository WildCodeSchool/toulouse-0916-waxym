package fr.wildcodeschool.haa.waxym;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import fr.wildcodeschool.haa.waxym.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LogActivity extends AppCompatActivity {
    private static final String TAG = "LogActivity";
    private Button btn_login;
    private EditText textEmailAddress;
    private EditText textPassword;
    private MessageDigest digest;
    private String encryptedPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        final String BASE_URL = "http://api.myservice.com/";

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        SuperInterface apiService = retrofit.create(SuperInterface.class);
        String password = "";
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        try {
            encryptedPassword = new String(hash,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        UserModel userModel = new UserModel("TestUser", this.encryptedPassword );
        Call<Long> call  = apiService.createRegister(userModel);

        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                long id = response.body();
                response.code();
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });
        //no action bar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_log);
        this.btn_login = (Button)findViewById(R.id.btn_login);
        this.textEmailAddress = (EditText)findViewById(R.id.input_email);

        this.textPassword = (EditText)findViewById(R.id.input_password);
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    public void login() {
        Log.d(TAG, "Login");

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
                        StatusSingleton.getInstance().setCurrentUserId(1);
                        onLoginSuccess();
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
        Intent intent = new Intent(this,MainActivity.class);
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

}
