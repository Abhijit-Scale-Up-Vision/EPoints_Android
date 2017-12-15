package com.iat.epoints.root;

import android.app.Application;

import com.iat.epoints.landing.LandingModule;
import com.iat.epoints.signin.SignInModule;
import com.iat.epoints.signup.SignUpModule;
import com.iat.epoints.thankyou.ThankYouModule;
/**
 * Created by Abhijit.
 */

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
                .signInModule(new SignInModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
