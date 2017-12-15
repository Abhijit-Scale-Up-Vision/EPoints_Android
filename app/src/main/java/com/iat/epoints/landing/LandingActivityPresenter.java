package com.iat.epoints.landing;

import android.support.annotation.Nullable;


/**
 * Created by Abhijit.
 */

public class LandingActivityPresenter implements LandingActivityMVP.Presenter {

    @Nullable
    private LandingActivityMVP.View view;
    private LandingActivityMVP.Model model;

    public LandingActivityPresenter(LandingActivityMVP.Model mockLandingModel) {
        this.model = mockLandingModel;
    }

    @Override
    public void setView(LandingActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void createAccountEmailButtonClicked() {
        view.gotoSignUp();
    }

    @Override
    public void signInButtonClicked() {
        model.signUpUser(true, false);
        view.gotoSignIn();
    }

    @Override
    public void facebookLoginClicked() {
        view.gotoFBLogin();
    }

    @Override
    public void getCurrentUser() {
        User user = model.getUser();

        if (user != null) {
            if (view != null) {
                if(user.isEmail())
                    view.gotoSignUp();
                else if(user.isFacebook()){//view.gotoFBLogin();
                }

            }
        }else {

        }
    }
}
