package com.iat.epoints.signin;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Manikanta.
 */

public class SignInActivityPresenter implements SignInActivityMVP.Presenter
{
    @Nullable
    private SignInActivityMVP.View view;
    private SignInActivityMVP.Model model;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;


    public SignInActivityPresenter(SignInActivityMVP.Model model)
    {
        this.model = model;
    }

    @Override
    public void setView(SignInActivityMVP.View view) {

        this.view = view;
    }

    @Override
    public void loginButtonClicked()
    {

        if (view != null)
        {
            if(!view.validate()){
                view.validationError();
            }
            else{
                model.signInUser(view.getEmail(), view.getPassword());
                model.getSignAccessToken(view.getToken());
                view.signInUser();
            }
        }
    }

    @Override
    public void getCurrentUser() {
        User user = model.getUser();

        if (user != null) {
            if (view != null) {

                view.setEmail(user.getEmail());
                view.setPassword(user.getPassword());

            }
        }else {

            view.showUserNotAvailable();

        }

    }

}
