package com.scaleup.epoints.root;

import com.scaleup.epoints.login.LoginActivity;
import com.scaleup.epoints.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity target);

}
