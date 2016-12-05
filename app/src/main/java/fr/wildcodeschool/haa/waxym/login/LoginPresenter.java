package fr.wildcodeschool.haa.waxym.login;

/**
 * Created by tuffery on 05/12/16.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View mView;


    public LoginPresenter(LoginContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void onLoginButtonClicked(String mail) {

    }

    @Override
    public void start() {

    }
}
