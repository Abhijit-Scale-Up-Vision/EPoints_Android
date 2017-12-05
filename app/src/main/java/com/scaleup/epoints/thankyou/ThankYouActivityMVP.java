package com.scaleup.epoints.thankyou;

/**
 * Created by Abhijit.
 */

public interface ThankYouActivityMVP {

    interface View{
        void gotoEmail();
    }
    interface Presenter{
        void setView(ThankYouActivityMVP.View view);

        void checkEmailButtonClicked();
    }
}
