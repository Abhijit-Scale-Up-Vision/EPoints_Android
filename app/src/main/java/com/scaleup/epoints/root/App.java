package com.scaleup.epoints.root;

import android.app.Application;

import com.scaleup.epoints.landing.LandingModule;
import com.scaleup.epoints.login.signup.SignUpModule;
import com.scaleup.epoints.thankyou.ThankYouModule;


public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .signUpModule(new SignUpModule())
                .landingModule(new LandingModule())
                .thankYouModule(new ThankYouModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
