package fr.wildcodeschool.haa.waxym.login;

/**
 * Created by tuffery on 05/12/16.
 */

public class LoginFragment implements LoginContract.View {
    private LoginContract.Presenter mPresenter;
    @Override public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onError() {

    }

    @Override
    public void onSuccess(String email) {

    }
}
