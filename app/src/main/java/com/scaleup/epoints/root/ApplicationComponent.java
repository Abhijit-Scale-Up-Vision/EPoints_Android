package com.scaleup.epoints.root;

import android.app.ListActivity;

import com.scaleup.epoints.http.apimodule.ApiModule;
import com.scaleup.epoints.landing.LandingActivity;
import com.scaleup.epoints.landing.LandingModule;
import com.scaleup.epoints.login.signup.SignUpActivity;
import com.scaleup.epoints.login.signup.SignUpModule;
import com.scaleup.epoints.thankyou.ThankYouActivity;
import com.scaleup.epoints.thankyou.ThankYouModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, SignUpModule.class, ApiModule.class, LandingModule.class, ThankYouModule.class})
public interface ApplicationComponent {

    void inject(SignUpActivity target);

    void inject(LandingActivity target);

    void inject(ThankYouActivity target);

}
