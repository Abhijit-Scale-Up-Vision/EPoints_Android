package com.scaleup.epoints.login.signup;

import dagger.Module;
import dagger.Provides;


@Module
public class SignUpModule {

    @Provides
    public SignUpActivityMVP.Presenter provideLoginActivityPresenter(SignUpActivityMVP.Model model){
        return new SignUpActivityPresenter(model);
    }

    @Provides
    public SignUpActivityMVP.Model provideLoginActivityModel(SignUpRepository repository){
        return new SignUpModel(repository);
    }

    @Provides
    public SignUpRepository provideLoginRepository(){
        return new MemoryRepository();
    }
}
