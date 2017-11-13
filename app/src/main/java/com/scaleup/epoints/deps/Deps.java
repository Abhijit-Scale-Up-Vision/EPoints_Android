package com.scaleup.epoints.deps;

import com.scaleup.epoints.home.HomeActivity;
import com.scaleup.epoints.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Abhijit.
 */
@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(HomeActivity homeActivity);
}
