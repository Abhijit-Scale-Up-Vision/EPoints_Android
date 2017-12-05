package com.scaleup.epoints.landing;

import com.scaleup.epoints.login.signup.SignUpActivityMVP;

/**
 * Created by Abhijit.
 */

public interface LandingActivityMVP {

    interface View{
        void gotoSignUp();
    }
    interface Presenter{
        void setView(LandingActivityMVP.View view);

        void createAccountEmailButtonClicked();
    }
}
