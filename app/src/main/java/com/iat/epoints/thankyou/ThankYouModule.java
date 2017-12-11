package com.iat.epoints.thankyou;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Abhijit.
 */

@Module
public class ThankYouModule {

    @Provides
    public ThankYouActivityMVP.Presenter provideThankYouActivityPresenter(){
        return new ThankYouActivityPresenter();
    }

}
