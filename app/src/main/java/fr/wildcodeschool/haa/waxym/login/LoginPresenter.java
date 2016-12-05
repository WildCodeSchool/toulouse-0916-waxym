package fr.wildcodeschool.haa.waxym.login;

import android.content.Context;
import android.content.Intent;

import fr.wildcodeschool.haa.waxym.MainActivity;

/**
 * Created by tuffery on 05/12/16.
 */

public class LoginPresenter implements LoginContract.Presenter {

    Context context;

    public LoginPresenter(Context context) {
        this.context = context;
    }



    @Override
    public void start() {

    }

    @Override
    public void onLoginButtonClicked() {
       Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);

    }
}
