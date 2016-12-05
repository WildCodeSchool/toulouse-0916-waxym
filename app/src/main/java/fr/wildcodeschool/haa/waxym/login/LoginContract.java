package fr.wildcodeschool.haa.waxym.login;

/**
 * Created by tuffery on 05/12/16.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {

    }
    interface Presenter extends BasePresenter {
        void onLoginButtonClicked();
    }
}