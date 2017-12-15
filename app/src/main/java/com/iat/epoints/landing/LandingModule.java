package com.iat.epoints.landing;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Abhijit.
 */

@Module
public class LandingModule {

    @Provides
    public LandingActivityMVP.Presenter provideLandingActivityPresenter(LandingActivityMVP.Model model){
        return new LandingActivityPresenter(model);
    }

    @Provides
    public LandingActivityMVP.Model provideLandingActivityModel(LandingRepository repository){
        return new LandingModel(repository);
    }

    @Provides
    public LandingRepository provideLandingRepository(){
        return new MemoryRepository();
    }

}
