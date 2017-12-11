package com.iat.epoints.landing;

/**
 * Created by Abhijit.
 */

public interface LandingActivityMVP {

    interface View{
        void gotoSignUp();

        void gotoSignIn();
    }
    interface Presenter{
        void setView(LandingActivityMVP.View view);

        void createAccountEmailButtonClicked();

        void signInButtonClicked();
    }
}