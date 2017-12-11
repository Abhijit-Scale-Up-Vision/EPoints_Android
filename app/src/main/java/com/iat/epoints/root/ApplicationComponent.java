package com.iat.epoints.root;

import com.iat.epoints.Utils.BaseActivity;
import com.iat.epoints.http.apimodule.ApiModule;
import com.iat.epoints.landing.LandingActivity;
import com.iat.epoints.landing.LandingModule;
import com.iat.epoints.login.signup.SignUpActivity;
import com.iat.epoints.login.signup.SignUpModule;
import com.iat.epoints.signIn.signIn.SignInActivity;
import com.iat.epoints.signIn.signIn.SignInModule;
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

}
