package com.iat.epoints.signIn.signIn;

import android.support.annotation.Nullable;

/**
 * Created by Manikanta.
 */

public class SignInActivityPresenter implements SignInActivityMVP.Presenter
{
    @Nullable
    private SignInActivityMVP.View view;
    private SignInActivityMVP.Model model;


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
                view.signInUser();
            }
        }
    }



}
