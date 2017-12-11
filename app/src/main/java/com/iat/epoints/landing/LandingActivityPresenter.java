package com.iat.epoints.landing;

import android.support.annotation.Nullable;


/**
 * Created by Abhijit.
 */

public class LandingActivityPresenter implements LandingActivityMVP.Presenter {

    @Nullable
    private LandingActivityMVP.View view;

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
        view.gotoSignIn();
    }
}
