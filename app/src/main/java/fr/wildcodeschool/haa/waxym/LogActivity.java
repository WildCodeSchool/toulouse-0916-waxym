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
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import fr.wildcodeschool.haa.waxym.model.UserModel;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import okhttp3.internal.framed.Header;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {


        /*Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();*/


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
        SuperInterface apiService = SuperInterface.retrofit.create(SuperInterface.class);
        UserModel userModel = new UserModel("TestUser", this.encryptedPassword );
        Call<IdModel> call  = apiService.newUser(userModel);
        new NetworkCall().execute(call);


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
    private class NetworkCall extends AsyncTask<Call, Void, Long> {
        @Override
        protected Long doInBackground(Call... params) {
            try {
                Call<IdModel> call = params[0];
                Response<IdModel> response = call.execute();
                int prout = response.code();
                Long id = response.body().getUserID();
                Headers headers = response.headers();
                   String message= response.message();
                   ResponseBody responseBody= response.errorBody();
                okhttp3.Response response1 = response.raw();
                return id;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Long result) {
            idTest = result;
        }
    }
}
