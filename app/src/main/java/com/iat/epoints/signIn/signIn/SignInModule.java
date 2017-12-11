package com.iat.epoints.signIn.signIn;


import dagger.Module;
import dagger.Provides;

/**
 * Created by Manikanta.
 */
@Module
public class SignInModule
{
    @Provides
    public SignInActivityMVP.Presenter provideSignInActivityPresenter(SignInActivityMVP.Model model){
        return new SignInActivityPresenter(model);
    }

    @Provides
    public SignInActivityMVP.Model provideSignInActivityModel(SignInRepository repository){
        return new SignInModel(repository);
    }

    @Provides
    public SignInRepository provideSignInRepository(){
        return new MemoryRepository();
    }

}
