package com.scaleup.epoints.thankyou;

import android.support.annotation.Nullable;


/**
 * Created by Abhijit.
 */

public class ThankYouActivityPresenter implements ThankYouActivityMVP.Presenter {

    @Nullable
    private ThankYouActivityMVP.View view;

    @Override
    public void setView(ThankYouActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void checkEmailButtonClicked() {
        view.gotoEmail();
    }
}
