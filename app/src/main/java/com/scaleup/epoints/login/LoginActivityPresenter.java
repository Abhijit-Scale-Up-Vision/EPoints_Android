package com.scaleup.epoints.login;

import android.support.annotation.Nullable;
import android.widget.Toast;

public class LoginActivityPresenter implements LoginActivityMVP.Presenter {

    @Nullable
    private LoginActivityMVP.View view;
    private LoginActivityMVP.Model model;

    public LoginActivityPresenter(LoginActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(LoginActivityMVP.View view) {

        this.view = view;

    }

    @Override
    public void loginButtonClicked() {

        if (view != null) {
           /* if (view.getFirstName().trim().equals("") || view.getLastName().trim().equals("") || view.getEmail().trim().equals("") || view.getPassword().trim().equals("")) {
                view.showInputError();
            } else {*/
                if(!view.validate()){
                    view.validationError();
                }
                else{
                    //onSignupSuccess();
                    model.createUser(view.getFirstName(), view.getLastName(), view.getEmail(), view.getPassword());
                    view.showUserSavedMessage();
                }

            //}

        }

    }

    @Override
    public void getCurrentUser() {

        User user = model.getUser();

        if (user != null) {
            if (view != null) {
                view.setFirstName(user.getFirstName());
                view.setLastName(user.getLastName());
                view.setEmail(user.getEmail());
                view.setPassword(user.getPassword());
            }
        }else {
            view.showUserNotAvailable();
        }

    }

    @Override
    public void saveUser() {

        if (view != null) {
            if (view.getFirstName().trim().equals("") || view.getLastName().trim().equals("")) {
                view.showInputError();
            } else {

                model.createUser(view.getFirstName(), view.getLastName(), view.getEmail(), view.getPassword());
                view.showUserSavedMessage();

            }
        }

    }
}
