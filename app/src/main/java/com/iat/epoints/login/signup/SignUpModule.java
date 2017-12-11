package com.iat.epoints.login.signup;

import dagger.Module;
import dagger.Provides;


@Module
public class SignUpModule {

    @Provides
    public SignUpActivityMVP.Presenter provideSignUpActivityPresenter(SignUpActivityMVP.Model model){
        return new SignUpActivityPresenter(model);
    }

    @Provides
    public SignUpActivityMVP.Model provideSignUpActivityModel(SignUpRepository repository){
        return new SignUpModel(repository);
    }

    @Provides
    public SignUpRepository provideSignUpRepository(){
        return new MemoryRepository();
    }
}
