package com.iat.epoints.landing;


/**
 * Created by Abhijit.
 */

public interface LandingActivityMVP {

    interface View{
        void gotoSignUp();
        void gotoSignIn();
        void gotoFBLogin();

        void signInSuccess(String token,int eTime);
        void gotoDashBoard(String email,String fName);
    }
    interface Presenter{
        void setView(LandingActivityMVP.View view);

        void createAccountEmailButtonClicked();
        void signInButtonClicked();
        void facebookLoginClicked();
        void getCurrentUser();
    }

    interface Model{
        void signUpUser(boolean email, boolean facebook);
        User getUser();
    }
}
