package fr.wildcodeschool.haa.waxym.login;


import android.app.Fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;

import fr.wildcodeschool.haa.waxym.R;


public class LogActivity extends AppCompatActivity {
    private static final String TAG = "LogActivity";
    private Button btn_login;
    private EditText textEmailAddress;
    private EditText textPassword;
    private LoginPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          Fragment  fragment = LoginFragment.getInstance();
            getFragmentManager().beginTransaction().add(android.R.id.content,fragment).commit();

        this.presenter = new LoginPresenter(this);
    }

}


