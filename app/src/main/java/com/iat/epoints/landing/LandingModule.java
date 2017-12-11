package com.iat.epoints.landing;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Abhijit.
 */

@Module
public class LandingModule {

    @Provides
    public LandingActivityMVP.Presenter provideLandingActivityPresenter(){
        return new LandingActivityPresenter();
    }

}
