package com.iat.epoints.root;

import com.iat.epoints.Utils.BaseActivity;
import com.iat.epoints.http.apimodule.ApiModule;
import com.iat.epoints.landing.LandingActivity;
import com.iat.epoints.landing.LandingModule;
import com.iat.epoints.service.LoginExpiryService;
import com.iat.epoints.signin.SignInActivity;
import com.iat.epoints.signup.SignUpActivity;
import com.iat.epoints.signup.SignUpModule;
import com.iat.epoints.signin.SignInModule;
import com.iat.epoints.thankyou.ThankYouActivity;
import com.iat.epoints.thankyou.ThankYouModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, SignUpModule.class, ApiModule.class, LandingModule.class, ThankYouModule.class, SignInModule.class})
public interface ApplicationComponent {

    void inject(SignUpActivity target);

    void inject(LandingActivity target);

    void inject(ThankYouActivity target);

    void inject(SignInActivity target);

    void inject(BaseActivity target);

    void inject(LoginExpiryService target);

}
