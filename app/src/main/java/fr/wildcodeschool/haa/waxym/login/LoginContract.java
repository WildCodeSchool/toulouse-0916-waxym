package fr.wildcodeschool.haa.waxym.login;

/**
 * Created by tuffery on 05/12/16.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void onError();
        void onSuccess(String email);
    }
    interface Presenter extends BasePresenter {
        void onLoginButtonClicked(String mail);
    }
}