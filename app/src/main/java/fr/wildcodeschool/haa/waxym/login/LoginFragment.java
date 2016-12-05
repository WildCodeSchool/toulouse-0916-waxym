package fr.wildcodeschool.haa.waxym.login;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fr.wildcodeschool.haa.waxym.R;

/**
 * Created by tuffery on 05/12/16.
 */

public class LoginFragment extends Fragment implements LoginContract.View {
    private LoginContract.Presenter mPresenter;
    private static LoginFragment INSTANCE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_log, container, false);
        Button btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginContract.Presenter)getView().getContext()).onLoginButtonClicked();
            }
        });
        return view;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static Fragment getInstance() {
        if (INSTANCE == null){
            INSTANCE = new LoginFragment();
        }
        return INSTANCE;
    }


}
